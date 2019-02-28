package com.zs.project.ui.fragment

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import cn.jzvd.JZUserAction
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.google.gson.reflect.TypeToken
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.bean.wangyi.VideoBean
import com.zs.project.event.RefreshEvent
import com.zs.project.request.RequestApi
import com.zs.project.util.*
import com.zs.project.view.MultiStateView
import kotlinx.android.synthetic.main.public_list_layout.*
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
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

    var mPageNum : Int = 0
    var mHolderPosition: Int? = null
//    var mPath = "Video_Recom"
    var mPath = "Video_Funny"


    var mFragment: VideoFragment ?= null
    var mAdapter: CommonAdapter<VideoBean>? = null
    var mData: MutableList<VideoBean> ?= null
    var mVideoImage = arrayListOf(R.mipmap.iv_video_img1,R.mipmap.iv_video_img2,R.mipmap.iv_video_img3,R.mipmap.iv_video_img4,R.mipmap.iv_video_img5,R.mipmap.iv_video_img6,R.mipmap.iv_video_img7,R.mipmap.iv_video_img8,R.mipmap.iv_video_img9)
    var mViewHolder : ViewHolder? = null


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

        mAdapter = object : CommonAdapter<VideoBean>(activity,mData,R.layout.item_video_layout){
            override fun convert(viewHolder: ViewHolder?, data: VideoBean?) {
                viewHolder?.setText(R.id.tv_voice_author,data?.topicName)
                viewHolder?.setText(R.id.tv_video_time, DateUtil.getStandTime1(data?.ptime))
                viewHolder?.setText(R.id.tv_player_num,data?.playCount)
                viewHolder?.setText(R.id.tv_video_length,StringUtils.getVideoLength(data?.length!!))

                var video = viewHolder?.getView<JZVideoPlayerStandard>(R.id.item_video_player)
                video?.setUp(data?.mp4_url, JZVideoPlayerStandard.SCREEN_WINDOW_LIST, data?.topicDesc?.trim())
                video?.titleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f)
                var position = if (viewHolder?.adapterPosition == null){
                    0
                }else{
                    viewHolder?.adapterPosition !! -1
                }
                ImageLoaderUtil.loadImage(data?.cover , video?.thumbImageView)
                video?.startButton?.setOnTouchListener { view, motionEvent ->
                    if (motionEvent?.action == MotionEvent.ACTION_UP){
                        mViewHolder = viewHolder
                        mHolderPosition = position
                    }
                    false
                }
                if (mHolderPosition != position){
                    viewHolder?.setVisible(R.id.tv_video_title,true)
                    viewHolder?.setVisible(R.id.tv_video_time,true)
                }

            }

        }

        JZVideoPlayerStandard.setJzUserAction { type, url, screen, objects ->

            when(type){
                JZUserAction.ON_CLICK_START_ICON , JZUserAction.ON_CLICK_RESUME ->{
                    mViewHolder?.setVisible(R.id.tv_video_title,false)
                    mViewHolder?.setVisible(R.id.tv_video_time,false)
                    LogUtil.logShow("gone")
                }
                JZUserAction.ON_CLICK_PAUSE ->{
                    mViewHolder?.setVisible(R.id.tv_video_title,true)
//                    mViewHolder?.setVisible(R.id.tv_video_time,true)
                    LogUtil.logShow("show")
                }
                JZUserAction.ON_AUTO_COMPLETE ->{
                    LogUtil.logShow("AUTO_COMPLETE")
                }
                JZUserAction.ON_CLICK_START_AUTO_COMPLETE ->{
                    LogUtil.logShow("CLICK_START_AUTO_COMPLETE")
                }
            }
        }

        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallBeat)
        recycler_view?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        RecyclerViewUtil.initNoDecoration(activity,recycler_view,mAdapter)
        recycler_view?.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewDetachedFromWindow(view: View?) {
                // 滑出不可见 退出到小窗口播放
                JZVideoPlayer.onChildViewDetachedFromWindow(view)

//                不可见就停止播放视频
//                val jzvd = view?.findViewById<JZVideoPlayerStandard>(R.id.item_video_player)
//                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd!!.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
//                    val currentJzvd = JZVideoPlayerManager.getCurrentJzvd()
//                    if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
//                        JZVideoPlayer.releaseAllVideos()
//                        LogUtil.logShow("onChildViewAttachedToWindow")
//                    }
//                }
            }

            override fun onChildViewAttachedToWindow(view: View?) {
                // 重新可见
                JZVideoPlayer.onChildViewAttachedToWindow(view, R.id.item_video_player)

            }

        })

        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                mPageNum += 10
                getVideoData()
            }

            override fun onRefresh() {
                mPageNum = 0
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

        mRequestApi.getRequestService(RequestApi.REQUEST_WANGYI)
                .getWangYIVideo(mPath , mPageNum, 10)
                .enqueue(object : retrofit2.Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        setError()
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        var back = response?.body()?.string()?.replace("videoList(","")?.trimEnd('）')
                        Log.d("My_Log", "back = $back")
                        try {
                            var obj = JSONObject(back)
                            var array = obj.getJSONArray(mPath)
                            mData = mGson.fromJson(array.toString() , object : TypeToken<MutableList<VideoBean>>(){}.type)
                            if (mData == null || mData?.size == 0) {
                                if (mPageNum == 0) {
                                    recycler_view?.refreshComplete()
                                } else {
                                    recycler_view?.setNoMore(true)
                                }
                            } else {
                                if (mPageNum == 0) {
                                    mAdapter?.datas = mData
                                    recycler_view?.refreshComplete()
                                } else {
                                    mAdapter?.addDatas(mData)
                                    recycler_view?.loadMoreComplete()
                                }
                            }
                            if (mAdapter?.itemCount == 0){
                                multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
                            }else{
                                multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                            }
                        } catch (e: Exception) {
                            setError()
                        }
                    }

                })


    }

    fun setError(){
        if (mAdapter?.itemCount!! > 0) {
            recycler_view?.reset()
        } else {
            multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
        }
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

    override fun onStop() {
        super.onStop()
        JZVideoPlayer.releaseAllVideos()
    }

}
