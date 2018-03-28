package com.zs.project.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.bean.video.ContentlistBean
import com.zs.project.bean.video.VideoData
import com.zs.project.request.DefaultObserver
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestHelper
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.view.MultiStateView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.public_list_layout.*
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

    var mFragment: VideoFragment ?= null
    var mAdapter: CommonAdapter<ContentlistBean>? = null
    var mData = mutableListOf<ContentlistBean>()

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
        multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT

        mAdapter = object : CommonAdapter<ContentlistBean>(activity,mData,R.layout.item_video_layout){
            override fun convert(viewHolder: ViewHolder?, data: ContentlistBean?) {
                viewHolder?.setText(R.id.tv_video_title,data?.text)
            }

        }
        recycler_view?.setPullRefreshEnabled(false)
        RecyclerViewUtil.initNoDecoration(activity,recycler_view,mAdapter)
    }

    override fun initData() {
        super.initData()

        val map = HashMap<String, String>()
        map["page"] = "1"
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

            }

            override fun onFail(response: VideoData?) {
                super.onFail(response)
                Log.d("My_Log","onFail")
            }

        })
    }


}
