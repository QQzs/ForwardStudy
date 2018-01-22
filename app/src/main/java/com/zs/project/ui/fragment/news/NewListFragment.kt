package com.zs.project.ui.fragment.news

import android.os.Bundle
import android.util.Log
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.LazyFragmentKotlin
import com.zs.project.bean.News.NewListBean
import com.zs.project.bean.News.NewListData
import com.zs.project.request.DefaultObserver
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestUtil
import com.zs.project.ui.adapter.NewListAdapter
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.view.MultiStateView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_fail.*
import kotlinx.android.synthetic.main.public_list_layout.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *
Created by zs
Date：2018年 01月 05日
Time：14:49
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class NewListFragment : LazyFragmentKotlin(), View.OnClickListener{

    var mIndex : Int = -1
    var mTitleName : String ?= null
    var mTitleCode : String ?= null
    var mFragment : NewListFragment ?= null

    var mAdapter : NewListAdapter ?= null
    var mStartNum : Int = 0

    var Get_LIST : Int = 111

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
        setContentView(R.layout.public_list_layout)
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
        loading_page_fail?.setOnClickListener(mFragment)
        mAdapter = NewListAdapter(ArrayList())
        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        RecyclerViewUtil.init(activity,recycler_view,mAdapter)
        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                mStartNum ++
                getData()
            }
            override fun onRefresh() {
                mStartNum = 0
                getData()
            }
        })
        multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING
        getData()
    }

    override fun getData() {
        super.getData()
        var map = HashMap<String, String>()
        map.put("channel", mTitleName!!)
        map.put("num", "20")
        map.put("start", (mStartNum * 20).toString())
        map.put("appkey", Constant.jcloudKey)
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_NEWS).newListDataRxjava(map),Get_LIST)
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.loading_page_fail ->{
                getData()
            }
        }
    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestUtil.getObservable(request)
        when(type){
            Get_LIST ->{
                observable.subscribe(object : DefaultObserver<NewListData>(mFragment){
                    override fun onSuccess(response: NewListData?) {
                        Log.d("My_Log",response.toString())
                        var listData : MutableList<NewListBean>? = response?.result?.result?.list

                        if (listData == null || listData.size == 0){
                            if (mStartNum == 0){
                                recycler_view?.refreshComplete()
                            }else{
                                recycler_view?.setNoMore(true)
                                recycler_view?.loadMoreComplete()
                            }
                        }else{
                            if (mStartNum == 0){
                                mAdapter?.updateData(listData)
                                recycler_view?.refreshComplete()
                            }else{
                                mAdapter?.appendData(listData)
                                recycler_view?.loadMoreComplete()
                            }
                        }

                        if (mAdapter?.itemCount == 0){
                            multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
                        }else{
                            multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
                    }
                })
            }
        }
    }


}