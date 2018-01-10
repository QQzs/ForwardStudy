package com.zs.project.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.ImageView

import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.ui.fragment.HomeFragment
import com.zs.project.ui.fragment.MeFragment
import com.zs.project.ui.fragment.NewsFragment

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Administrator
 */
class MainActivity : BaseActivity() {

    private var mTransaction: FragmentTransaction? = null
    private var mCurrentFragment: Fragment? = null
    private var mHomeFragment: HomeFragment? = null
    private var mNewsFragment: NewsFragment? = null
    private var mMeFragment: MeFragment? = null
    private var mFragments : ArrayList<Fragment> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_main)
    }

    override fun init() {

        mTransaction = supportFragmentManager.beginTransaction()
        mHomeFragment = HomeFragment()
        mNewsFragment = NewsFragment()
        mMeFragment = MeFragment()

        mFragments = ArrayList()
        mFragments?.add(mHomeFragment!!)
        mFragments?.add(mNewsFragment!!)
        mFragments?.add(mMeFragment!!)

        changePage(0)

        fragment_home?.setOnClickListener(this)
        fragment_news?.setOnClickListener(this)
        fragment_me?.setOnClickListener(this)

    }

    override fun initData() {

    }

    /**
     * 切换page
     */
    private fun changePage(index : Int){
        changeTab(index)
        changeFragment(mFragments?.get(index))
    }

    /**
     * fragment切换
     * @param nextFragment
     */
    private fun changeFragment(nextFragment: Fragment?) {
        if (nextFragment != null) {
            if (mCurrentFragment != null) {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.hide(mCurrentFragment).commitAllowingStateLoss()
            }
            mCurrentFragment = nextFragment
            var transaction = supportFragmentManager.beginTransaction()
            if (nextFragment.isAdded) {
                transaction.show(nextFragment).commitAllowingStateLoss()
            } else {
                transaction.add(R.id.fl_homepage, nextFragment).commitAllowingStateLoss()
            }
        }
    }

    private fun changeTab(index : Int){
        when(index){
            0 ->{
                iv_home_product.setBackgroundResource(R.mipmap.home_bar_home_sel)
                tv_product.setTextColor(Color.parseColor("#7365f0"))
                iv_home_news.setBackgroundResource(R.mipmap.home_bar_news_nor)
                tv_news.setTextColor(Color.parseColor("#929292"))
                iv_home_me.setBackgroundResource(R.mipmap.home_bar_user_nor)
                tv_me.setTextColor(Color.parseColor("#929292"))
            }
            1 ->{
                iv_home_product.setBackgroundResource(R.mipmap.home_bar_home_nor)
                tv_product.setTextColor(Color.parseColor("#929292"))
                iv_home_news.setBackgroundResource(R.mipmap.home_bar_news_sel)
                tv_news.setTextColor(Color.parseColor("#7365f0"))
                iv_home_me.setBackgroundResource(R.mipmap.home_bar_user_nor)
                tv_me.setTextColor(Color.parseColor("#929292"))
            }
            2 ->{
                iv_home_product.setBackgroundResource(R.mipmap.home_bar_home_nor)
                tv_product.setTextColor(Color.parseColor("#929292"))
                iv_home_news.setBackgroundResource(R.mipmap.home_bar_news_nor)
                tv_news.setTextColor(Color.parseColor("#929292"))
                iv_home_me.setBackgroundResource(R.mipmap.home_bar_user_sel)
                tv_me.setTextColor(Color.parseColor("#7365f0"))
            }
        }
    }

    fun startAnimation(view: ImageView) {
        val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1f, 0.95f, 1f)
        scaleXAnimator.repeatCount = 0
        //沿y轴放大
        val scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1f, 0.95f, 1f)
        scaleYAnimator.repeatCount = 0
        val set = AnimatorSet()
        //同时沿X,Y轴放大
        set.play(scaleXAnimator).with(scaleYAnimator)
        //都设置1s，也可以为每个单独设置
        set.duration = 300
        set.startDelay = 100
        set.start()
    }


    override fun requestData(request: Observable<*>, type: Int) {

    }

    override fun onClick(view: View) {

        when(view.id){
            R.id.fragment_home ->{
                changePage(0)
                startAnimation(iv_home_product)
            }
            R.id.fragment_news ->{
                changePage(1)
                startAnimation(iv_home_news)
            }
            R.id.fragment_me ->{
                changePage(2)
                startAnimation(iv_home_me)
            }
        }

    }
}
