package com.zs.project.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.kelin.banner.BannerEntry
import com.kelin.banner.view.BannerView
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.bean.Movie.MovieDetailData
import com.zs.project.bean.Movie.MovieListData
import com.zs.project.bean.MovieBannerEntry
import com.zs.project.event.RefreshEvent
import com.zs.project.listener.KotlinItemClickListener
import com.zs.project.request.DefaultObserver
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestUtil
import com.zs.project.ui.activity.WebViewActivity
import com.zs.project.ui.activity.test.TestActivity
import com.zs.project.ui.adapter.DouBanAdapter
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.transform.DepthPageTransformer
import com.zs.project.view.MultiStateView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dou_header_view_layout.view.*
import kotlinx.android.synthetic.main.public_list_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class DouBanFragment : BaseFragment() , KotlinItemClickListener {

    var mTheaterMovie : MutableList<MovieDetailData> ?= null
    var mComingMovie : MutableList<MovieDetailData> ?= null
    var mTopMovie : MutableList<MovieDetailData> ?= null

    var mFragment : DouBanFragment ?= null
    var mAdapter : DouBanAdapter ?= null
    var mHeadView : View ?= null

    var THEATER_MOVIE : Int = 111
    var COMING_MOVIE : Int = 222
    var TOP_MOVIE : Int = 333

    var mStartNum : Int = 0

    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_home_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragment = this
        initView()
        initData()
    }

    override fun initView() {
        super.initView()
        mHeadView = View.inflate(activity,R.layout.dou_header_view_layout,null)

    }

    override fun initData() {
        super.initData()
        multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING
        requestData(mRequestApi
                .getRequestService(RequestApi.REQUEST_DOUBAN)
                .getMovieListData(Constant.MOVIE_THEATERS,0,5)
                ,THEATER_MOVIE)

    }

    override fun onItemClick(position: Int, data: Any) {

        var url = (data as MovieDetailData).alt
        activity?.startActivity<WebViewActivity>("url" to url)

    }

    fun initBanner(){
        mHeadView?.banner_view_top?.setPageTransformer(true, DepthPageTransformer())
        mHeadView?.banner_view_top?.entries = getbannerData(mTheaterMovie!!)
        mHeadView?.banner_view_top?.setOnPageClickListener(object : BannerView.OnPageClickListener(){
            override fun onPageClick(entry: BannerEntry<*>?, index: Int) {

                var bannerEntry : MovieBannerEntry = entry as MovieBannerEntry
                activity?.toast(bannerEntry.movieId)
                activity?.startActivity<TestActivity>()
            }

        })
        getMovieTop250()
        recycler_view?.addHeaderView(mHeadView)
        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallGridPulse)
        mAdapter = DouBanAdapter(ArrayList(),this)
        RecyclerViewUtil.initNoDecoration(activity,recycler_view,mAdapter)
        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                mStartNum ++
                getMovieTop250()
            }

            override fun onRefresh() {
                mStartNum = 0
                getMovieTop250()            }

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
    }


    /**
     * 获取banner数据
     */
    fun getbannerData(data : List<MovieDetailData>) : MutableList<MovieBannerEntry>{
        var items = ArrayList<MovieBannerEntry>()
        data.mapTo(items) { MovieBannerEntry(it.title, it.id, it.images.large) }
        return items
    }

    /**
     * 获取top250电影数据
     */
    fun getMovieTop250(){
        requestData(mRequestApi
                .getRequestService(RequestApi.REQUEST_DOUBAN)
                .getMovieListData(Constant.MOVIE_TOP,mStartNum * 15,15)
                ,TOP_MOVIE)
    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestUtil.getObservable(request)
        when(type){
            THEATER_MOVIE ->{
                observable.subscribe(object : DefaultObserver<MovieListData>(mFragment) {
                    override fun onSuccess(response: MovieListData?) {
                        if (response != null){
                            mTheaterMovie = response?.subjects
                            initBanner()
                        }
                    }
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        recycler_view?.reset()
                        multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
                    }

                })
            }
            TOP_MOVIE ->{
                observable.subscribe(object : DefaultObserver<MovieListData>(mFragment){
                    override fun onSuccess(response: MovieListData?) {
                        if (response == null){
                            return
                        }
                        mTopMovie = response?.subjects
                        if (mTopMovie == null || mTopMovie?.size == 0){
                            if (mStartNum == 0){
                                recycler_view?.refreshComplete()
                            }else{
                                recycler_view?.setNoMore(true)
                                recycler_view?.loadMoreComplete()
                            }

                        }else{
                            if (mStartNum == 0){
                                mAdapter?.refreshData(mTopMovie!!)
                                recycler_view?.refreshComplete()
                            }else{
                                mAdapter?.appendData(mTopMovie!!)
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
