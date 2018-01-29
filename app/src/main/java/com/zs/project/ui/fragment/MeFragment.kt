package com.zs.project.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.request.RequestApi
import okhttp3.ResponseBody
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

class MeFragment : BaseFragment() {

    var mFragment : MeFragment ?= null
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
        initData()
    }

    override fun initData() {
        super.initData()

        var listDataCall = mRequestApi.getRequestService(RequestApi.REQUEST_DOUBAN).getMovieListData(Constant.MOVIE_THEATERS,"0","1")
        listDataCall.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("My_Log","error")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("My_Log",response?.body()?.string())
            }

        })

        var newDataCall = mRequestApi.getRequestService(RequestApi.REQUEST_DOUBAN).getMovieListData(Constant.MOVIE_COMING,"0","1")
        newDataCall.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("My_Log","error")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("My_Log",response?.body()?.string())
            }

        })

        var newDataCall2 = mRequestApi.getRequestService(RequestApi.REQUEST_DOUBAN).getMovieListData(Constant.MOVIE_TOP,"0","1")
        newDataCall2.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("My_Log","error")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("My_Log",response?.body()?.string())
            }

        })


    }

}
