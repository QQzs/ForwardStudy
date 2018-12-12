package com.zs.project.ui.fragment.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.google.gson.reflect.TypeToken
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.zs.project.R
import com.zs.project.base.LazyFragmentKotlin
import com.zs.project.event.RefreshEvent
import com.zs.project.greendao.ArticleData
import com.zs.project.greendao.GreenDaoManager
import com.zs.project.listener.ItemClickListener
import com.zs.project.listener.ItemLongClickListener
import com.zs.project.request.RequestApi
import com.zs.project.ui.activity.ImageShowActivity
import com.zs.project.ui.adapter.ArticleAdapter
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.SnackbarUtils
import com.zs.project.view.MultiStateView
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
Created by zs
Date：2018年 01月 05日
Time：14:49
—————————————————————————————————————
About: 资讯列表界面
—————————————————————————————————————
 */
class WangYiFragment : LazyFragmentKotlin(), View.OnClickListener, ItemClickListener, ItemLongClickListener {

    /**
     *  继承懒加载fragment LazyFragmentKotlin
     *  不能使用Kotlin的anko库来查找控件,会找不到控件
     */
    var mTitleName: String? = null
    var mTitleCode: String? = null
    var mStartNum: Int = 0
    var mCurrentPosition = 0

    var mFragment: WangYiFragment? = null
    var mAdapter: ArticleAdapter? = null
    var mArticleData: MutableList<ArticleData>? = null

    var multistate_view: MultiStateView? = null
    var recycler_view: XRecyclerView? = null
    var loading_page_fail: RelativeLayout? = null

    companion object {
        fun getInstance(vararg arg: String): WangYiFragment {
            var fragment = WangYiFragment()
            if (arg.isNotEmpty()) {
                var bundle = Bundle()
                if (arg.isNotEmpty()) {
                    bundle.putString("name", arg[0])
                }
                if (arg.size > 1) {
                    bundle.putString("code", arg[1])
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
    }

    override fun initData() {
        super.initData()
        mTitleName = arguments?.getString("name")
        mTitleCode = arguments?.getString("code")

        loading_page_fail?.setOnClickListener(mFragment)
        mAdapter = ArticleAdapter(context , this , this)
        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        recycler_view?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        RecyclerViewUtil.init(activity, recycler_view, mAdapter)
        getData()
        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                mStartNum += 10
                getData()
            }

            override fun onRefresh() {
                mStartNum = 0
                getData()
            }
        })

        var linearLayoutManager = recycler_view?.layoutManager as LinearLayoutManager
        recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 20) {
                    EventBus.getDefault().post(RefreshEvent("scroll", false))
                } else if (dy < -20) {
                    EventBus.getDefault().post(RefreshEvent("scroll", true))
                }

                //找到列表下一个可见的View
                var view = linearLayoutManager?.findViewByPosition(mCurrentPosition + 1) ?: return
                //判断是否需要更新悬浮条
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()){
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition()

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
        mRequestApi.getRequestService(RequestApi.REQUEST_WANGYI)
                .getWangYI(mTitleCode, mStartNum, 10)
                .enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                setError()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                var back = response?.body()?.string()?.replace("artiList(","")?.trimEnd('）')
                Log.d("My_Log", "back = $back")
                try {
                    var obj = JSONObject(back)
                    var array = obj.getJSONArray(mTitleCode)
                    mArticleData = mGson.fromJson<MutableList<ArticleData>>(array.toString() , object : TypeToken<MutableList<ArticleData>>(){}.type)
                    if (mArticleData == null || mArticleData?.size == 0) {
                        if (mStartNum == 0) {
                            recycler_view?.refreshComplete()
                        } else {
                            recycler_view?.setNoMore(true)
                            recycler_view?.loadMoreComplete()
                        }
                    } else {
                        if (mStartNum == 0) {
                            mAdapter?.updateData(mArticleData!!)
                            recycler_view?.refreshComplete()
                        } else {
                            mAdapter?.appendData(mArticleData!!)
                            recycler_view?.loadMoreComplete()
                        }
                    }
                    if (mAdapter?.itemCount == 0) {
                        multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
                    } else {
                        multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                    }
                } catch (e: Exception) {
                    setError()
                }

            }

        })

    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.loading_page_fail -> {
                getData()
            }
        }
    }

    override fun onItemClick(position: Int, data: Any, view: View) {
        when (view.id) {
            R.id.iv_article_img , R.id.cl_image_item ->{
                val intent = Intent(activity, ImageShowActivity::class.java)
                intent.putExtra(PublicFieldUtil.URL_FIELD, data as String)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity as Activity,
                        view.findViewById(view.id),
                        getString(R.string.transition_image)
                )
                ActivityCompat.startActivity(activity as Activity, intent, optionsCompat.toBundle())
            }
        }

    }

    override fun onItemLongClick(position: Int, data: Any, view: View) {

        GreenDaoManager.getInstance().session.articleDataDao.insertOrReplace(data as ArticleData)
        dynamicAddView(multistate_view,"snackBar",R.color.app_main_color)
        SnackbarUtils.Short(multistate_view, "收藏成功~")
                .show()

    }

    fun setError(){
        if (mAdapter?.itemCount!! > 0) {
            recycler_view?.reset()
        } else {
            multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
        }
    }


}