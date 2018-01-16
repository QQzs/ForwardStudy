package com.zs.project.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout

/**
 * <h1>懒加载Fragment</h1> 只有创建并显示的时候才会调用onCreateViewLazy方法<br></br>
 * <br></br>
 *
 *
 * 懒加载的原理onCreateView的时候Fragment有可能没有显示出来。<br></br>
 * 但是调用到setUserVisibleHint(boolean isVisibleToUser),isVisibleToUser =
 * true的时候就说明有显示出来<br></br>
 * 但是要考虑onCreateView和setUserVisibleHint的先后问题所以才有了下面的代码
 *
 *
 * 注意：<br></br>
 * 《1》原先的Fragment的回调方法名字后面要加个Lazy，比如Fragment的onCreateView方法， 就写成onCreateViewLazy <br></br>
 * 《2》使用该LazyFragment会导致多一层布局深度
 *
 *
 * LuckyJayce
 */
open class LazyFragmentKotlin : BaseFragment() {
    private var isInit = false
    private var savedInstanceState: Bundle? = null
    private var isLazyLoad = true
    private var layout: FrameLayout? = null

    private var isStart = false

    @Deprecated("")
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        val bundle = arguments
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
        }
        if (isLazyLoad) {
            if (userVisibleHint && !isInit) {
                isInit = true
                onCreateViewLazy(savedInstanceState)
            } else {
                var layoutInflater: LayoutInflater? = inflater
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(activity)
                }
                layout = FrameLayout(layoutInflater?.context)
                val view = getPreviewLayout(layoutInflater, layout)
                if (view != null) {
                    layout?.addView(view)
                }
                layout?.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                super.setContentView(layout)
            }
        } else {
            isInit = true
            onCreateViewLazy(savedInstanceState)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isInit && contentView != null) {
            isInit = true
            onCreateViewLazy(savedInstanceState)
            onResumeLazy()
        }
        if (isInit && contentView != null) {
            if (isVisibleToUser) {
                isStart = true
                onFragmentStartLazy()
            } else {
                isStart = false
                onFragmentStopLazy()
            }
        }
    }

    protected fun getPreviewLayout(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return null
    }

    @Deprecated("")
    override fun onStart() {
        super.onStart()
        if (isInit && !isStart && userVisibleHint) {
            isStart = true
            onFragmentStartLazy()
        }
    }

    @Deprecated("")
    override fun onStop() {
        super.onStop()
        if (isInit && isStart && userVisibleHint) {
            isStart = false
            onFragmentStopLazy()
        }
    }

    protected open fun onFragmentStartLazy() {

    }

    protected open fun onFragmentStopLazy() {

    }

    protected open fun onCreateViewLazy(savedInstanceState: Bundle?) {

    }

    protected open fun onResumeLazy() {

    }

    protected open fun onPauseLazy() {

    }

    protected open fun onDestroyViewLazy() {

    }

    override fun setContentView(layoutResID: Int) {
        if (isLazyLoad && contentView != null && contentView.parent != null) {
            layout!!.removeAllViews()
            val view = inflater.inflate(layoutResID, layout, false)
            layout!!.addView(view)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View) {
        if (isLazyLoad && contentView != null && contentView.parent != null) {
            layout!!.removeAllViews()
            layout!!.addView(view)
        } else {
            super.setContentView(view)
        }
    }

    @Deprecated("")
    override fun onResume() {
        super.onResume()
        if (isInit) {
            onResumeLazy()
        }
    }

    @Deprecated("")
    override fun onPause() {
        super.onPause()
        if (isInit) {
            onPauseLazy()
        }
    }

    @Deprecated("")
    override fun onDestroyView() {
        super.onDestroyView()
        if (isInit) {
            onDestroyViewLazy()
        }
        isInit = false
    }

    companion object {
        val INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad"
        var mHandler = Handler()
    }
}
