package com.zs.project.ui.activity.news

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.google.gson.reflect.TypeToken
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.bean.Channel
import com.zs.project.bean.News.QaTagInfo
import com.zs.project.event.RefreshEvent
import com.zs.project.listener.ItemDragHelperCallBack
import com.zs.project.listener.OnTagChangeListener
import com.zs.project.ui.adapter.QaTagAdapter
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.SharedPreferencesUtil
import com.zs.project.util.StringUtils
import kotlinx.android.synthetic.main.activity_choose_tag_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*
import org.greenrobot.eventbus.EventBus

/**
 *
Created by zs
Date：2018年 01月 22日
Time：16:40
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ChooseTagActivity : BaseActivity() , OnTagChangeListener{

    var mSelectTitles : MutableList<Channel> = ArrayList()
    var mUnSelectTitles : MutableList<Channel> = ArrayList()
    var mAllTagTitles: MutableList<Channel> = ArrayList()  // 所有标签
    var mAdapter : QaTagAdapter ?= null
    var mHelper: ItemTouchHelper? = null
    var mSpanCount = 3
    var mChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_choose_tag_layout)
    }

    override fun init() {

        mAdapter = QaTagAdapter(this , mAllTagTitles)
        RecyclerViewUtil.Gridinit(this,tag_recycler_view , mAdapter , mSpanCount)
        tag_recycler_view?.adapter = mAdapter

        var layoutManager : GridLayoutManager = tag_recycler_view?.layoutManager as GridLayoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val itemViewType = mAdapter?.getItemViewType(position)
                return if (itemViewType == QaTagInfo.TYPE_MY_CHANNEL || itemViewType == QaTagInfo.TYPE_OTHER_CHANNEL || itemViewType == QaTagInfo.TYPE_NORMAL) 1 else mSpanCount
            }
        }
        val callBack = ItemDragHelperCallBack(this)
        mHelper = ItemTouchHelper(callBack)
        mAdapter?.setOnTagChangeListener(this)
        mHelper?.attachToRecyclerView(tag_recycler_view)

        iv_all_back?.setOnClickListener(this)

    }

    override fun initData() {

        val selectTitle = SharedPreferencesUtil.getString(PublicFieldUtil.TITLE_SELECTED, "")
        val unselectTitle = SharedPreferencesUtil.getString(PublicFieldUtil.TITLE_UNSELECTED, "")
        if (StringUtils.isNullOrEmpty(selectTitle) || StringUtils.isNullOrEmpty(unselectTitle)) {
            var titleName = resources.getStringArray(R.array.category_name)
            var titleCode = resources.getStringArray(R.array.category_type)
            for (i in titleCode.indices){
                mSelectTitles.add(Channel(titleName[i],titleCode[i],Channel.TYPE_MY_CHANNEL))
            }
            SharedPreferencesUtil.setString(PublicFieldUtil.TITLE_SELECTED , mGson.toJson(mSelectTitles))
        } else {
            var select = mGson.fromJson<List<Channel>>(selectTitle , object : TypeToken<List<Channel>>(){}.type)
            val unSelecte = mGson.fromJson<List<Channel>>(unselectTitle, object : TypeToken<List<Channel>>() {
            }.type)

            mSelectTitles.addAll(select)
            mUnSelectTitles.addAll(unSelecte)
        }
        mAllTagTitles.add(Channel("已关注的资讯",Channel.TYPE_MY))
        mAllTagTitles.addAll(mSelectTitles)
        mAllTagTitles.add(Channel("推荐资讯",Channel.TYPE_OTHER))
        mAllTagTitles.addAll(mUnSelectTitles)

        mAdapter?.updateData(mAllTagTitles)

    }


    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_all_back ->{
                finish()
            }
        }
    }

    override fun onStarDrag(baseViewHolder: RecyclerView.ViewHolder?) {
        mHelper?.startDrag(baseViewHolder)
        mChanged = true
    }

    override fun onItemMove(starPos: Int, endPos: Int) {
        //我的频道之间移动
        onMove(starPos, endPos)
    }

    private fun onMove(starPos: Int, endPos: Int) {
        val startChannel = mAllTagTitles.get(starPos)
        //先删除之前的位置
        mAllTagTitles.removeAt(starPos)
        //添加到现在的位置
        mAllTagTitles.add(endPos, startChannel)
        mAdapter?.notifyItemMoved(starPos, endPos)
        mChanged = true
    }

    override fun onMoveToMyChannel(starPos: Int, endPos: Int) {
        // 移动到已关注的标签
        onMove(starPos, endPos)
    }

    override fun onMoveToOtherChannel(starPos: Int, endPos: Int) {
        // 移动到推荐标签
        onMove(starPos, endPos)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mChanged){
            var selectTitles = mAdapter?.myChannel
            var unSelectTitles = mAdapter?.otherChannel
            SharedPreferencesUtil.setString(PublicFieldUtil.TITLE_SELECTED,mGson.toJson(selectTitles))
            SharedPreferencesUtil.setString(PublicFieldUtil.TITLE_UNSELECTED,mGson.toJson(unSelectTitles))
            EventBus.getDefault().post(RefreshEvent("title",true))
        }
    }

}