package com.zs.project.ui.activity.test

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.zs.project.R
import com.zs.project.ui.adapter.MeAdapter
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.ScreenUtil
import kotlinx.android.synthetic.main.activity_test_scroll.*

/**
 *
Created by zs
Date：2018年 02月 01日
Time：17:00
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TestScrollActivity : AppCompatActivity(){


    var mAdapter : MeAdapter?= null
    var mData : MutableList<String> ?= null

    var mHandler : Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_scroll)

        mData = ArrayList()
        for ( i in 1..10){
            mData!!.add("")
        }
        mAdapter = MeAdapter(mData!!)
        RecyclerViewUtil.init(this,scroll_view_pull,mAdapter)

        var zoomHeader = View.inflate(this,R.layout.zoom_header_layout,null)
        scroll_view_pull?.addZoomHeaderView(zoomHeader,ScreenUtil.dp2px(180f))

        scroll_view_pull?.setPullRefreshEnabled(true)
        scroll_view_pull?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        scroll_view_pull?.setLoadingMoreProgressStyle(ProgressStyle.BallGridPulse)

        scroll_view_pull?.setLoadingListener(object : XRecyclerView.LoadingListener{

            override fun onRefresh() {
                var data = ArrayList<String>()
                for ( i in 1..10){
                    data!!.add("")
                }
                mHandler.postDelayed({
                    mAdapter?.refreshData(data)
                    scroll_view_pull?.refreshComplete()
                },1000)

            }

            override fun onLoadMore() {
                var data = ArrayList<String>()
                for ( i in 1..10){
                    data!!.add("")
                }
                mHandler.postDelayed({
                    mAdapter?.appendData(data)
                    scroll_view_pull?.loadMoreComplete()
                },1000)

            }

        })
    }


}