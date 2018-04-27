package com.zs.project.ui.fragment

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.bean.android.ArticleList
import com.zs.project.listener.ItemClickListener
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestHelper
import com.zs.project.request.bean.BaseResponseAndroid
import com.zs.project.request.cookie.DefaultObserverAndroid
import com.zs.project.util.LogUtil
import io.reactivex.Observable

/**
 *
Created by zs
Date：2018年 04月 27日
Time：17:31
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticleFragment : BaseFragment() , ItemClickListener {


    var ARTICLE_ANDROID: Int = 5000

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)

        setContentView(R.layout.fragment_home_layout)
        initData()

    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()

        requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).getArticleList(0),ARTICLE_ANDROID)

    }


    override fun onItemClick(position: Int, data: Any, view: View) {


    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestHelper.getObservable(request)
        observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<ArticleList>>(this){
            override fun onSuccess(response: BaseResponseAndroid<ArticleList>?) {
                when(type){
                    ARTICLE_ANDROID ->{
                        var articleList = response?.data?.datas

                        if (articleList != null && articleList.size > 0){
                            var article = articleList!![0]
                            LogUtil.logShow("title = " + article.title + "  id = " + article.id)
                        }else{
                            LogUtil.logShow("nulllllllllll")

                        }


                    }

                }
            }

        })
    }

}