package com.zs.project.ui.activity

import android.os.Bundle
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.bean.android.Article
import com.zs.project.bean.android.ArticleList
import com.zs.project.event.RefreshEvent
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestHelper
import com.zs.project.request.bean.BaseResponseAndroid
import com.zs.project.request.cookie.DefaultObserverAndroid
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.view.MultiStateView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.public_list_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *
Created by zs
Date：2018年 05月 03日
Time：16:46
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class CollectionActivity : BaseActivity(){

    var mStartNum: Int = 0
    var mCollectPosition: Int = -1

    var mAdapter: CommonAdapter<Article>? = null
    var mData = mutableListOf<Article>()

    var ARTICLE_COLLECT_LIST: Int = 600
    var ARTICLE_COLLECT_ANDROID: Int = 6001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_collect_list)

    }

    override fun init() {

        tv_all_title?.text = "我的文章"
        iv_all_back?.setOnClickListener(this)

        multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING

    }

    override fun initData() {

        mAdapter = object : CommonAdapter<Article>(this,mData,R.layout.item_article_layout){
            override fun convert(viewHolder: ViewHolder?, data: Article?) {

                viewHolder?.setText(R.id.tv_article_author,data?.author)
                viewHolder?.setText(R.id.tv_article_time,data?.niceDate)

                viewHolder?.setText(R.id.tv_article_title,data?.title)
                viewHolder?.setText(R.id.tv_article_chapter_name,data?.chapterName)

                dynamicAddView(viewHolder?.getView(R.id.iv_article_collect),"switchColor",R.color.app_main_color)
                viewHolder?.setOnClickListener(R.id.iv_article_collect){
                    if (mCollectPosition != -1){
                        return@setOnClickListener
                    }else{
                        // header
                        mCollectPosition = viewHolder?.adapterPosition - 1
                        unCollectArticle(data!!.id , data!!.originId)
                    }
                }

                viewHolder?.setOnClickListener(R.id.card_article_view) {
                    startActivity<WebViewActivity>("url" to data?.link)
                }

            }

        }

        recycler_view?.setLoadingMoreProgressStyle(ProgressStyle.BallBeat)
        recycler_view?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        RecyclerViewUtil.initNoDecoration(this,recycler_view,mAdapter)

        recycler_view?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                mStartNum ++
                getCollectList()
            }

            override fun onRefresh() {
                mStartNum = 0
                getCollectList()
            }

        })

        getCollectList()
    }

    /**
     * 获取文章列表
     */
    private fun getCollectList(){
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).getCollectList(mStartNum),ARTICLE_COLLECT_LIST)
    }

    /**
     * 取消收藏文章
     */
    private fun unCollectArticle(id : Int , originId : Int){
        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).unCollectArticleList(id,originId),ARTICLE_COLLECT_ANDROID)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_all_back ->{
                finish()
            }
        }
    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestHelper.getObservable(request)
        when(type){
            ARTICLE_COLLECT_LIST ->{
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
            ARTICLE_COLLECT_ANDROID ->{
                observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<Object>>(this){
                    override fun onSuccess(response: BaseResponseAndroid<Object>?) {
                        mAdapter?.remove(mCollectPosition)
                        mCollectPosition = -1
                        toast("取消收藏")
                    }

                })

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 通知文章列表刷新
        EventBus.getDefault().post(RefreshEvent("article" , true))
    }

}
