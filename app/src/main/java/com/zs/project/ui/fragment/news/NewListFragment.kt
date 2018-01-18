package com.zs.project.ui.fragment.news

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.LazyFragmentKotlin
import com.zs.project.bean.News.NewListData
import com.zs.project.request.DefaultObserver
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestUtil
import io.reactivex.Observable
import java.util.*

/**
 *
Created by zs
Date：2018年 01月 05日
Time：14:49
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class NewListFragment : LazyFragmentKotlin(){

    var mIndex : Int = -1
    var mTitleName : String ?= null
    var mTitleCode : String ?= null
    var mFragment : NewListFragment ?= null

    companion object {
        fun getInstance(vararg arg: String) : NewListFragment{
            var fragment = NewListFragment()
            if (arg.isNotEmpty()){
                var bundle = Bundle()
                if (arg.isNotEmpty()){
                    bundle.putString("index",arg[0])
                }
                if (arg.size > 1){
                    bundle.putString("name",arg[1])
                }
                if (arg.size > 2){
                    bundle.putString("code",arg[2])
                }
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        setContentView(R.layout.fragment_new_list_layout)
        mFragment = this
        initData()
        if (mIndex > 0){
            initView()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 第一页的内容在这个方法中加载，因为在onCreateViewLazy中初始化的时候，控件是找不到的
        if (mIndex == 0){
            initView()
        }
    }

    override fun initData() {
        super.initData()
        mIndex = arguments.getString("index").toInt()
        mTitleName = arguments.getString("name")
        mTitleCode = arguments.getString("code")

        Log.d("My_Log","initData mTitleName = " + mTitleName)
    }

    override fun initView() {
        super.initView()
        getData()
    }

    override fun getData() {
        super.getData()
        var map = HashMap<String, String>()
        map.put("channel", mTitleName!!)
        map.put("num", "20")
        map.put("start", "0")
        map.put("appkey", Constant.jcloudKey)
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_NEWS).newListDataRxjava(map),111)
    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestUtil.getObservable(request)
        observable.subscribe(object : DefaultObserver<NewListData>(mFragment){
            override fun onSuccess(response: NewListData?) {
                Log.d("My_Log",response.toString())
            }
        })


    }


}