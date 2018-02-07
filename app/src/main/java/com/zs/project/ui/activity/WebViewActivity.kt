package com.zs.project.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.view.MultiStateView
import kotlinx.android.synthetic.main.activity_web_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*

/**
 *
Created by zs
Date：2018年 02月 05日
Time：15:39
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class WebViewActivity : BaseActivity(){

    var mWebUrl : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_web_layout)
    }

    override fun init() {
        iv_all_back?.setOnClickListener(this)
    }

    override fun initData() {

        mWebUrl = intent.getStringExtra("url")

        //声明WebSettings子类
        var webSettings = web_view_detail?.settings

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings?.javaScriptEnabled = true
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
//        webSettings?.setPluginsEnabled(true)

        //设置自适应屏幕，两者合用
        webSettings?.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings?.loadWithOverviewMode = true // 缩放至屏幕的大小

        //缩放操作
        webSettings?.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings?.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings?.displayZoomControls = false //隐藏原生的缩放控件

        //其他细节操作
        webSettings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings?.allowFileAccess = true //设置可以访问文件
        webSettings?.javaScriptCanOpenWindowsAutomatically = true//支持通过JS打开新窗口
        webSettings?.loadsImagesAutomatically = true //支持自动加载图片
        webSettings?.defaultTextEncodingName = "utf-8"//设置编码格式

        // 滚动条
//        web_view_detail?.isHorizontalScrollBarEnabled = false //水平不显示
        web_view_detail?.isVerticalScrollBarEnabled = false //垂直不显示

        web_view_detail?.loadUrl(mWebUrl)
        // 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        web_view_detail?.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                multistate_view?.viewState = MultiStateView.VIEW_STATE_LOADING
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                web_view_detail?.loadUrl(mWebUrl)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
            }

        }




    }

    override fun onClick(view: View?) {
        when(view?.id){

        }

    }


}
