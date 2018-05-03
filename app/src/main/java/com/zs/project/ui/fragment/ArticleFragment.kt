package com.zs.project.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.bean.MovieBannerEntry
import com.zs.project.bean.android.Article
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import com.zs.project.event.LoginEvent
import com.zs.project.event.RefreshEvent
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestHelper
import com.zs.project.request.bean.BaseResponseAndroid
import com.zs.project.request.cookie.DefaultObserverAndroid
import com.zs.project.ui.activity.LoginActivity
import com.zs.project.ui.activity.WebViewActivity
import com.zs.project.ui.activity.test.TestAndroidActivity
import com.zs.project.util.LogUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.StringUtils
import com.zs.project.util.transform.DepthPageTransformer
import com.zs.project.view.MultiStateView
import com.zs.project.view.banner.BannerEntry
import com.zs.project.view.banner.view.BannerView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dou_header_view_layout.view.*
import kotlinx.android.synthetic.main.public_list_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *
Created by zs
Date：2018年 04月 27日
Time：17:31
—————————————————————————————————————
About: 玩android 文章列表
—————————————————————————————————————
 */
class ArticleFragment : BaseFragment() {

    var mStartNum: Int = 0
    var mCollectPosition: Int = -1;

    var mFragment: ArticleFragment? = null
    var mAdapter: CommonAdapter<Article>? = null
    var mHeadView : View ?= null
    var mData = mutableListOf<Article>()
    var mBannerData = mutableListOf<ArticleBanner>()

    var ARTICLE_BANNER_ANDROID: Int = 5000
    var ARTICLE_ANDROID: Int = 5001
    var ARTICLE_COLLECT_ANDROID: Int = 5002

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_home_layout)
        EventBus.getDefault().register(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragment = this
        initView()
        initData()
    }

    override fun initView() {
        super.initView()
        multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING

        mHeadView = View.inflate(activity,R.layout.dou_header_view_layout,null)
        mHeadView?.tv_banner_title?.visibility = View.GONE

    }

    override fun initData() {
        super.initData()

        mAdapter = object : CommonAdapter<Article>(activity,mData,R.layout.item_article_layout){
            override fun convert(viewHolder: ViewHolder?, data: Article?) {

                viewHolder?.setText(R.id.tv_article_author,data?.author)
                viewHolder?.setText(R.id.tv_article_time,data?.niceDate)

                viewHolder?.setText(R.id.tv_article_title,data?.title)
                viewHolder?.setText(R.id.tv_article_chapter_name,data?.chapterName)

                if (data!!.collect){
                    dynamicAddView(viewHolder?.getView(R.id.iv_article_collect),"switchColor",R.color.app_main_color)
                }else{
                    dynamicAddView(viewHolder?.getView(R.id.iv_article_collect),"switchColor",R.color.main_color_gray)
                }
                viewHolder?.setOnClickListener(R.id.iv_article_collect){
                    if (StringUtils.isNullOrEmpty(mUserId)){
                        activity?.startActivity<LoginActivity>()
                    }else if (mCollectPosition != -1){
                        return@setOnClickListener
                    }else{
                        // header 和 banner
                        mCollectPosition = viewHolder?.adapterPosition -2
                        LogUtil.logShow("index = " + mCollectPosition)
                        collectArticle(data?.id)
                    }
                }

                viewHolder?.setOnClickListener(R.id.card_article_view) {

//                    activity?.startActivity<WebViewActivity>("url" to data?.link)
                    activity?.startActivity<TestAndroidActivity>()
                }

            }

        }
        recycler_view?.addHeaderView(mHeadView)
        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallBeat)
        recycler_view?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        RecyclerViewUtil.initNoDecoration(activity,recycler_view,mAdapter)

        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                mStartNum ++
                getArticleData()
            }

            override fun onRefresh() {
                mStartNum = 0
                getArticleData()
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

        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).articleBanner,ARTICLE_BANNER_ANDROID)
        getArticleData()
    }

    /**
     * 获取文章列表
     */
    private fun getArticleData(){
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).getArticleList(mStartNum),ARTICLE_ANDROID)
    }

    /**
     * 收藏文章
     */
    private fun collectArticle(id : Int){
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).collectArticle(id),ARTICLE_COLLECT_ANDROID)
    }

    fun initBanner(){

        mHeadView?.banner_view_top?.setPageTransformer(true, DepthPageTransformer())
        mHeadView?.banner_view_top?.entries = getbannerData(mBannerData)
        mHeadView?.banner_view_top?.setOnPageClickListener(object : BannerView.OnPageClickListener(){
            override fun onPageClick(entry: BannerEntry<*>?, index: Int) {

                var bannerEntry : MovieBannerEntry = entry as MovieBannerEntry
                var url = bannerEntry.alt
                activity?.startActivity<WebViewActivity>("url" to url)
            }

        })

    }

    /**
     * 获取banner数据
     */
    private fun getbannerData(data : List<ArticleBanner>) : MutableList<MovieBannerEntry>{
        var items = ArrayList<MovieBannerEntry>()
        data.mapTo(items) { MovieBannerEntry(it.title, it.id, it.imagePath,it.url) }
        return items
    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestHelper.getObservable(request)
        when(type){
            ARTICLE_ANDROID ->{
                observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<ArticleList>>(this){
                    override fun onSuccess(response: BaseResponseAndroid<ArticleList>?) {
                        var articleList = response?.data?.datas
                        if (articleList != null && articleList.size > 0){
                            if (mStartNum == 0){
                                mAdapter?.datas = articleList
                                recycler_view?.refreshComplete()
                            }else{
                                mAdapter?.addDatas(articleList)
                                recycler_view?.loadMoreComplete()
                            }

                        }else{
                            if (mStartNum == 0){
                                recycler_view?.refreshComplete()
                            }else{
                                recycler_view?.setNoMore(true)
                            }
                        }
                        if ( mAdapter != null && mAdapter?.itemCount!! > 0){
                            multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                        }else{
                            multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
                        }

                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        if (mAdapter != null && mAdapter?.itemCount!! > 0){
                            recycler_view?.reset()
                        }else{
                            multistate_view?.viewState = MultiStateView.VIEW_STATE_ERROR
                        }
                    }

                })
            }
            ARTICLE_BANNER_ANDROID ->{
                observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<MutableList<ArticleBanner>>>(this){
                    override fun onSuccess(response: BaseResponseAndroid<MutableList<ArticleBanner>>?) {
                        mBannerData = response?.data as MutableList<ArticleBanner>
                        if (mBannerData != null && mBannerData.size > 0){
                            initBanner()
                        }else{
                            activity?.toast("banner数据错误")
                        }
                    }

                })
            }
            ARTICLE_COLLECT_ANDROID ->{

                observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<Object>>(this){
                    override fun onSuccess(response: BaseResponseAndroid<Object>?) {
                        if (mAdapter?.datas != null && mCollectPosition < mAdapter?.datas!!.size && mCollectPosition != -1 ){
                            var article = mAdapter?.datas?.get(mCollectPosition)
                            var isCollect = article?.collect
                            article?.collect = !isCollect!!
                            mAdapter?.datas?.set(mCollectPosition,article)
                            mAdapter?.notifyDataSetChanged()
                            if (!isCollect){
                                activity?.toast("收藏成功")
                            }else{
                                activity?.toast("取消收藏")
                            }
                            mCollectPosition = -1
                        }
                    }

                })

            }

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginOrExit(event : LoginEvent){
        mStartNum = 0
        getArticleData()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}