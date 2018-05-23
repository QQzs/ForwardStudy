package com.zs.project.ui.activity.test

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.request.RequestApi
import com.zs.project.util.RecyclerViewUtil
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

    var mData = mutableListOf<String>()
    var mAdapter: CommonAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_android_layout)
    }


    override fun init() {

    }

    override fun initData() {

        mData.add("登录")
        mData.add("退出")
        mData.add("收藏列表")
        mData.add("文章")
        mData.add("收藏")
        mData.add("EY——Login")
        mData.add("EY--User")

        mAdapter = object : CommonAdapter<String>(this,mData,R.layout.item_test_layout){
            override fun convert(viewHolder: ViewHolder?, item: String?) {
                var position = if (viewHolder?.adapterPosition == null){
                    0
                }else{
                    viewHolder?.adapterPosition
                }
                viewHolder?.setText(R.id.tv_test,item)
                viewHolder?.setOnClickListener(R.id.tv_test, View.OnClickListener {
                    when(position){
                        0 ->{
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
                        1 ->{
                            SpUtil.clearAll()
                            Log.d("My_Log","clear")
                        }
                        2 ->{
                            var collection = mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).getColllectListTest(0)
                            collection.enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                    Log.d("My_Log","error")
                                }

                                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                    Log.d("My_Log","back = " + response?.body()?.string())
                                }

                            })
                        }
                        3 ->{
                            var article = mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).getArticleListTest(0)
                            article.enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                    Log.d("My_Log","error")
                                }

                                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                    Log.d("My_Log","back = " + response?.body()?.string())
                                }

                            })
                        }
                        4 ->{
                            var article = mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).collectArticleTest(1165)
                            article.enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                    Log.d("My_Log","error")
                                }

                                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                    Log.d("My_Log","back = " + response?.body()?.string())
                                }

                            })
                        }
                        5 ->{
                            var token = mRequestApi.getRequestService(0).getToken("lawyer2","123456","password","RMS")
                            token.enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                    Log.d("My_Log","error")
                                }

                                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                    Log.d("My_Log","back = " + response?.body()?.string())
                                }

                            })
                        }
                        6 ->{
                            var user = mRequestApi.getRequestService(0).getUser("lawyer2")
                            user.enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                    Log.d("My_Log","error")
                                }

                                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                    Log.d("My_Log","back = " + response?.body()?.string())
                                }

                            })
                        }
                    }
                })

            }

        }
        RecyclerViewUtil.initGrid(this,test_recycker_view,mAdapter,3)

    }

    override fun onClick(view: View?) {

    }

}