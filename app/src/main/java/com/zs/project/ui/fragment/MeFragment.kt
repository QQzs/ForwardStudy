package com.zs.project.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.request.RequestApi
import com.zs.project.ui.adapter.MeAdapter
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.view.scrollview.ScrollableLayout
import com.zs.project.view.scrollview.ViewHelper
import kotlinx.android.synthetic.main.fragment_me_layout.*
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class MeFragment : BaseFragment() , View.OnClickListener{
    var mFragment : MeFragment ?= null
    var mFlag : Boolean = true
    var mAdapter : MeAdapter ?= null
    var mData : MutableList<String> ?= null
    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_me_layout)
        mFragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun initView() {
        super.initView()
        iv_avatar_img?.setOnClickListener(this)

        mData = ArrayList()
        for ( i in 1..10){
            mData!!.add("")
        }
        mAdapter = MeAdapter(mData!!)
        RecyclerViewUtil.init(activity,recycler_view_me,mAdapter)
        recycler_view_me?.setPullRefreshEnabled(false)
        scroll_view?.setOnScrollListener(ScrollableLayout.OnScrollListener {
            currentY, maxY -> ViewHelper.setTranslationY(iv_avatar_img, currentY * 0.7f)
        })
        scroll_view?.setCurrentScrollableContainer(recycler_view_me)
        scroll_view?.setClickHeadExpand(60)


    }

    override fun initData() {
        super.initData()

        var listDataCall = mRequestApi.getRequestService(RequestApi.REQUEST_DOUBAN).getTestData(Constant.MOVIE_THEATERS,0,1)
        listDataCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("My_Log","error")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("My_Log",response?.body()?.string())
            }

        })

    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_avatar_img ->{
                activity?.toast("hhh")
                mFlag = !mFlag
                colorfull_bg_view?.switchAnim(mFlag)
            }

//            R.id.tv_bottom ->{
//                activity?.toast("wwwwwww")
//            }

        }

    }

}
