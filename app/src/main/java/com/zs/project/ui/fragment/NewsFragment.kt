package com.zs.project.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.zs.project.R
import com.zs.project.R.id.indicator_layout
import com.zs.project.base.BaseFragment
import com.zs.project.bean.Channel
import com.zs.project.ui.fragment.news.NewListFragment
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.SharedPreferencesUtil
import com.zs.project.util.StringUtils
import com.zs.project.view.topscorllview.indicator.IndicatorViewPager
import com.zs.project.view.topscorllview.indicator.slidebar.ColorBar
import com.zs.project.view.topscorllview.indicator.slidebar.ScrollBar
import com.zs.project.view.topscorllview.indicator.transition.OnTransitionTextListener
import kotlinx.android.synthetic.main.fragment_news_layout.*
import org.jetbrains.anko.dip

/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class NewsFragment : BaseFragment() {

    var mSelectTitles : ArrayList<Channel> = ArrayList()
    var mUnSelectTitles : ArrayList<Channel> = ArrayList()
    var mAdapter : MyAdapter ?= null
    var mIndicatorViewPager : IndicatorViewPager ?= null

    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_news_layout)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun initData() {
        super.initData()
        val selectTitle = SharedPreferencesUtil.getString(PublicFieldUtil.TITLE_SELECTED, "")
        val unselectTitle = SharedPreferencesUtil.getString(PublicFieldUtil.TITLE_UNSELECTED, "")
        if (StringUtils.isNullOrEmpty(selectTitle) || StringUtils.isNullOrEmpty(unselectTitle)) {
            var titleName = resources.getStringArray(R.array.category_name)
            var titleCode = resources.getStringArray(R.array.category_type)
            for (i in titleCode.indices){
                mSelectTitles.add(Channel(titleName[i],titleCode[i]))
            }
            SharedPreferencesUtil.setString(PublicFieldUtil.TITLE_SELECTED , mGson.toJson(mSelectTitles))
        } else {
            var select = mGson.fromJson<List<Channel>>(selectTitle , object : TypeToken<List<Channel>>(){}.type)
            val unSelecte = mGson.fromJson<List<Channel>>(unselectTitle, object : TypeToken<List<Channel>>() {
            }.type)

            mSelectTitles.addAll(select)
            mUnSelectTitles.addAll(unSelecte)
        }

        indicator_layout?.isSplitAuto = false
        indicator_layout?.setPinnedTabView(false)
        var colorBar = ColorBar(activity,R.color.colorBar, activity.dip(2f), ScrollBar.Gravity.BOTTOM)
        indicator_layout?.setScrollBar(colorBar)
        indicator_layout?.onTransitionListener = OnTransitionTextListener().setColor(activity.resources.getColor(R.color.colorBar)
        , activity.resources.getColor(R.color.defaultText))

        vp_more_tab?.offscreenPageLimit = 15
        mIndicatorViewPager = IndicatorViewPager(indicator_layout,vp_more_tab)
        mAdapter = MyAdapter(activity.supportFragmentManager)
        mIndicatorViewPager?.adapter = mAdapter

    }

    inner class MyAdapter(fragmentManager: FragmentManager?) : IndicatorViewPager.IndicatorFragmentPagerAdapter(fragmentManager) {


        override fun getCount(): Int {
            return mSelectTitles.size
        }

        override fun getViewForTab(position: Int, convertView: View?, container: ViewGroup?): View {
            var view = LayoutInflater.from(activity).inflate(R.layout.tab_title_layout,container,false)
            var textView : TextView = view as TextView
            textView.text = mSelectTitles[position].getTitleName()
            return view

        }

        override fun getFragmentForPage(position: Int): Fragment {

            var title = mSelectTitles[position]
            return NewListFragment.getInstance(position.toString(),title.titleName,title.titleCode)
        }

        override fun getItemPosition(obj : Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

    }


}
