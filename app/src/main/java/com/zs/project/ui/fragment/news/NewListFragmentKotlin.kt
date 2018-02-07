package com.zs.project.ui.fragment.news

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.LazyFragmentKotlin
import com.zs.project.bean.News.NewListBean
import com.zs.project.bean.News.NewListData
import com.zs.project.event.RefreshEvent
import com.zs.project.listener.KotlinItemClickListener
import com.zs.project.request.DefaultObserver
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestUtil
import com.zs.project.ui.activity.WebViewActivity
import com.zs.project.ui.adapter.NewListAdapter
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.StringUtils
import com.zs.project.view.MultiStateView
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.HashMap

/**
 *
Created by zs
Date：2018年 01月 05日
Time：14:49
—————————————————————————————————————
About: 资讯列表界面
—————————————————————————————————————
 */
class NewListFragmentKotlin : LazyFragmentKotlin(), View.OnClickListener , KotlinItemClickListener {

    /**
     *  继承懒加载fragment LazyFragmentKotlin
     *  不能使用Kotlin的anko库来查找控件,会找不到控件
     */

    var mIndex : Int = -1
    var mTitleName : String ?= null
    var mTitleCode : String ?= null
    var mStartNum : Int = 0

    var mFragment : NewListFragmentKotlin?= null
    var mAdapter : NewListAdapter ?= null

    var multistate_view : MultiStateView ?= null
    var recycler_view : XRecyclerView ?= null
    var loading_page_fail : RelativeLayout?= null

    var Get_LIST : Int = 111

    companion object {
        fun getInstance(vararg arg: String) : NewListFragmentKotlin {
            var fragment = NewListFragmentKotlin()
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
        initView()
        initData()
        Log.d("My_Log","   mIndex =   " + mIndex)
    }

    override fun initData() {
        super.initData()
        var index = arguments?.getString("index")
        if(StringUtils.isNullOrEmpty(index)){
            mIndex = index!!.toInt()
        }
        mTitleName = arguments?.getString("name")
        mTitleCode = arguments?.getString("code")

        loading_page_fail?.setOnClickListener(mFragment)
        mAdapter = NewListAdapter(ArrayList(),this)
        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        recycler_view?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        RecyclerViewUtil.init(activity,recycler_view,mAdapter)
        getData()
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
        multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING

    }

    override fun initView() {
        super.initView()
        multistate_view = findViewById(R.id.multistate_view)
        recycler_view = findViewById(R.id.recycler_view)
        loading_page_fail = findViewById(R.id.loading_page_fail)

    }

    override fun getData() {
        super.getData()
        var map = HashMap<String,Any>()
        map.put("channel", mTitleName!!)
        map.put("num", 20)
        map.put("start", mStartNum * 20)
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

    override fun onItemClick(position: Int, data: Any) {

//        Snackbar.make(multistate_view!!,"dddd",Snackbar.LENGTH_SHORT).show()

//        val imageView = ImageView(context)
//        imageView.setImageResource(R.mipmap.default_img)
//        SnackbarUtils.Short(multistate_view!!,"ffffff")
//                .show()

        var url = (data as NewListBean).weburl
        activity!!.startActivity<WebViewActivity>("url" to url)
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
                        if (mAdapter?.itemCount!! > 0){
                            recycler_view?.reset()
                        }else{
                            multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
                        }
                    }
                })
            }
        }
    }


}