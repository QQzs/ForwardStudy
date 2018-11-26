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
import com.zs.project.base.BaseFragment
import com.zs.project.bean.Channel
import com.zs.project.event.RefreshEvent
import com.zs.project.ui.activity.news.ChooseTagActivity
import com.zs.project.ui.fragment.news.NewListFragmentKotlin
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.SpUtil
import com.zs.project.util.StringUtils
import com.zs.project.view.topscorllview.indicator.IndicatorViewPager
import kotlinx.android.synthetic.main.fragment_news_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class NewsFragment : BaseFragment() , View.OnClickListener{
    var mSelectTitles : MutableList<Channel> = ArrayList()
    var mAdapter : MyAdapter ?= null
    var mIndicatorViewPager : IndicatorViewPager ?= null

    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_news_layout)
        EventBus.getDefault().register(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun initData() {
        super.initData()

        iv_add_tab?.setOnClickListener(this)

        initTitleData()
        indicator_layout?.isSplitAuto = false
        indicator_layout?.setPinnedTabView(false)
//        var colorBar = ColorBar(activity, ContextCompat.getColor(activity!!,R.color.app_main_color), activity!!.dip(2f), ScrollBar.Gravity.BOTTOM)
//        indicator_layout?.setScrollBar(colorBar)
//        indicator_layout?.onTransitionListener = OnTransitionTextListener().setColor(ContextCompat.getColor(activity!!,R.color.app_main_color)
//        , ContextCompat.getColor(activity!!,R.color.font_default))
        dynamicAddView(indicator_layout,"colorBar",R.color.app_main_color)
        dynamicAddView(indicator_layout,"scrollIndicator",R.color.app_main_color)

        vp_more_tab?.offscreenPageLimit = 15
        mIndicatorViewPager = IndicatorViewPager(indicator_layout,vp_more_tab)
        mAdapter = MyAdapter(activity!!.supportFragmentManager)
        mIndicatorViewPager?.adapter = mAdapter

    }

    /**
     * 获取标签数据
     */
    private fun initTitleData(){
        val selectTitle = SpUtil.getString(PublicFieldUtil.TITLE_SELECTED, "")
        if (StringUtils.isNullOrEmpty(selectTitle)) {
            var titleName = resources.getStringArray(R.array.category_name)
            var titleCode = resources.getStringArray(R.array.category_type)
            for (i in titleCode.indices){
                mSelectTitles.add(Channel(titleName[i],titleCode[i]))
            }
            SpUtil.setString(PublicFieldUtil.TITLE_SELECTED , mGson.toJson(mSelectTitles))
        } else {
            var select = mGson.fromJson<List<Channel>>(selectTitle , object : TypeToken<List<Channel>>(){}.type)
            mSelectTitles.clear()
            mSelectTitles.addAll(select)
        }
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_add_tab ->{
                activity!!.startActivity<ChooseTagActivity>()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshTitle(event : RefreshEvent){
        if (event != null && event.getFlag() == "title" && event.isRefresh){
            initTitleData()
            mAdapter?.notifyDataSetChanged()
            mIndicatorViewPager?.setCurrentItem(0,false)
        }
    }

    inner class MyAdapter(fragmentManager: FragmentManager?) : IndicatorViewPager.IndicatorFragmentPagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return mSelectTitles.size
        }

        override fun getViewForTab(position: Int, convertView: View?, container: ViewGroup?): View {
            var view : View ?= null
            if (convertView == null){
                view = LayoutInflater.from(activity).inflate(R.layout.tab_title_layout,container,false)
            }else{
                view = convertView
            }
            var textView : TextView = view as TextView
            textView.text = mSelectTitles[position].getTitleName()
            return view

        }

        override fun getFragmentForPage(position: Int): Fragment {

            var title = mSelectTitles[position]
            return NewListFragmentKotlin.getInstance(position.toString(),title.titleName,title.titleCode)

        }

        override fun getItemPosition(obj : Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
