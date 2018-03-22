package com.zs.project.ui.activity.test

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.request.RequestApi
import com.zs.project.util.SpUtil
import kotlinx.android.synthetic.main.activity_android_layout.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
Created by zs
Date：2018年 03月 22日
Time：10:03
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TestAndroidActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_android_layout)
    }


    override fun init() {

        btn_1?.setOnClickListener(this)
        btn_2?.setOnClickListener(this)
        btn_3?.setOnClickListener(this)

    }

    override fun initData() {
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.btn_1 ->{
                var login = mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).login("疯狂的兔子666" , "147258369")
                login.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Log.d("My_Log","error")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        Log.d("My_Log","back = " + response?.body()?.string())
                    }

                })
            }
            R.id.btn_2 ->{
                SpUtil.clearAll()
                Log.d("My_Log","clear")
            }
            R.id.btn_3 ->{
                var collection = mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).getColllectList(0)
                collection.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Log.d("My_Log","error")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        Log.d("My_Log","back = " + response?.body()?.string())
                    }

                })
            }
        }
    }

}