package com.zs.project.ui.fragment

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import cn.jzvd.*
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.bean.video.ContentlistBean
import com.zs.project.bean.video.VideoData
import com.zs.project.event.RefreshEvent
import com.zs.project.request.DefaultObserver
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestHelper
import com.zs.project.util.DateUtil
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.view.MultiStateView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.public_list_layout.*
import org.greenrobot.eventbus.EventBus
import java.util.*



/**
 *
Created by zs
Date：2018年 03月 27日
Time：16:02
—————————————————————————————————————
About: 视频Fragment
———————————————F——————————————————————
 */
class VideoFragment : BaseFragment(){

    var mPageNum : Int = 1

    var mFragment: VideoFragment ?= null
    var mAdapter: CommonAdapter<ContentlistBean>? = null
    var mData = mutableListOf<ContentlistBean>()

    var mVideoUrl = "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4"

    val DATA_VIDEO : Int = 2000

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_video_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragment = this
        initView()
        initData()

    }

    override fun initView() {
        super.initView()
        multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING

        mAdapter = object : CommonAdapter<ContentlistBean>(activity,mData,R.layout.item_video_layout){
            override fun convert(viewHolder: ViewHolder?, data: ContentlistBean?) {
                viewHolder?.setText(R.id.tv_video_title,data?.text?.trim())
                viewHolder?.setText(R.id.tv_voice_author,data?.name)
                viewHolder?.setText(R.id.tv_video_time,DateUtil.friendlyTime(data?.create_time))
                viewHolder?.setText(R.id.tv_voice_like,data?.love)
                viewHolder?.setText(R.id.tv_video_follow,(Random().nextInt(200) + 100).toString())

                var header = viewHolder?.getView<ImageView>(R.id.iv_video_header)
                ImageLoaderUtil.loadAvatarImage(data?.profile_image,header)
                var video = viewHolder?.getView<JZVideoPlayerStandard>(R.id.item_video_player)
                video?.setUp(data?.video_uri, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
//                video?.setUp(mVideoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
                ImageLoaderUtil.displayLocalImage(R.mipmap.iv_video_img1,video?.thumbImageView)

//                JZVideoPlayerStandard.setJzUserAction { type, url, screen, objects ->
//                    when(type){
//                        JZUserAction.ON_CLICK_START_ICON ->{
//                            viewHolder?.setVisible(R.id.tv_video_title,false)
//                            viewHolder?.setVisible(R.id.tv_video_time,false)
//                        }
//                        JZUserAction.ON_CLICK_PAUSE ->{
//                            viewHolder?.setVisible(R.id.tv_video_title,true)
//                            viewHolder?.setVisible(R.id.tv_video_time,true)
//                        }
//                    }
//                }

            }

        }
        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallBeat)
        recycler_view?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        RecyclerViewUtil.initNoDecoration(activity,recycler_view,mAdapter)
        recycler_view?.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewDetachedFromWindow(view: View?) {
            }

            override fun onChildViewAttachedToWindow(view: View?) {
                val jzvd = view?.findViewById<JZVideoPlayerStandard>(R.id.item_video_player)
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd!!.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                    val currentJzvd = JZVideoPlayerManager.getCurrentJzvd()
                    if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                        JZVideoPlayer.releaseAllVideos()
                    }
                }
            }

        })

        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                mPageNum ++
                getVideoData()
            }

            override fun onRefresh() {
                mPageNum = 1
                getVideoData()
            }

        })
        recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 20) {
                    EventBus.getDefault().post(RefreshEvent("scroll", false))
                } else if (dy < -20) {
                    EventBus.getDefault().post(RefreshEvent("scroll", true))
                }
            }

        })

    }

    override fun initData() {
        super.initData()
        getVideoData()
    }

    fun getVideoData(){
        val map = HashMap<String, String>()
        map["page"] = mPageNum.toString()
        map["showapi_appid"] = Constant.showapi_appid
        map["showapi_sign"] = Constant.showapi_sign
        map["type"] = "41"
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_SHOW).getVideoListData(map),DATA_VIDEO)

    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestHelper.getObservable(request)
        observable.subscribe(object : DefaultObserver<VideoData>(mFragment){
            override fun onSuccess(response: VideoData?) {

                mData = response?.showapi_res_body?.pagebean?.contentlist!!
                mAdapter?.datas = mData
                Log.d("My_Log","d = " + mData.toString())

                if (mData.size == 0) {
                    if (mPageNum == 1) {
                        recycler_view?.refreshComplete()
                    } else {
                        recycler_view?.setNoMore(true)
                    }

                } else {
                    if (mPageNum == 1) {
                        mAdapter?.datas = mData
                        recycler_view?.refreshComplete()
                    } else {
                        mAdapter?.addDatas(mData)
                        recycler_view?.loadMoreComplete()
                    }
                }

                if (mAdapter?.datas?.size == 0){
                    multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
                }else{
                    multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                }


            }

            override fun onFail(response: VideoData?) {
                super.onFail(response)
                multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
                Log.d("My_Log","onFail")
            }

        })
    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoUrl
     * @return
     */
    fun getNetVideoBitmap(videoUrl: String): Bitmap? {
        var startTime = System.currentTimeMillis()
        var bitmap: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, HashMap())
            //获得第一帧图片
            bitmap = retriever.frameAtTime
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        var endTime = System.currentTimeMillis()
        return bitmap
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }


}
