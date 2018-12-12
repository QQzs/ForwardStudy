package com.zs.project.ui.activity.setting

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.ViewGroup
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.OnItemClickListener
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.app.Constant.iconId
import com.zs.project.app.Constant.iconNames
import com.zs.project.base.BaseActivity
import com.zs.project.bean.ColorBean
import com.zs.project.event.RefreshEvent
import com.zs.project.util.SpUtil
import com.zs.project.view.swipecard.CardConfig
import com.zs.project.view.swipecard.OverLayCardLayoutManager
import com.zs.project.view.swipecard.RenRenCallback
import kotlinx.android.synthetic.main.public_list_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*
import org.greenrobot.eventbus.EventBus

/**
 *
Created by zs
Date：2018年 02月 08日
Time：15:15
—————————————————————————————————————
About: 选图标页面
—————————————————————————————————————
 */
class IconChooseActivity : BaseActivity(){

    var mFlag : Int = 0
    var mAdapter: CommonAdapter<ColorBean>?= null
    var mDatas: MutableList<ColorBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.public_list_activity_layout)
    }

    override fun init() {

        tv_all_title?.text = "选择图标"
        iv_all_back?.setOnClickListener(this)

    }

    override fun initData() {

        for (i in iconId.indices){
            mDatas.add(ColorBean(i , iconId[i], iconNames[i]))
        }
        mFlag = SpUtil.getInt("color_view",0)
        mAdapter = object : CommonAdapter<ColorBean>(this,mDatas,R.layout.item_color_choose_layout){

            override fun convert(viewHolder: ViewHolder?, colorBean: ColorBean?) {

                viewHolder?.setImageResource(R.id.iv_icon_img, iconId[colorBean!!.index])
                viewHolder?.setText(R.id.tv_icon_name,colorBean?.name)
                viewHolder?.setVisible(R.id.iv_choose_check , colorBean!!.isChoose)

//                viewHolder?.setOnClickListener(R.id.cv_color_item , {
//                    mFlag = colorBean!!.index
//                    refreshData()
//                })

            }

        }
        mAdapter?.setOnItemClickListener(object : OnItemClickListener<ColorBean> {

            override fun onItemClick(p0: ViewGroup?, p1: View?, p2: ColorBean?, p3: Int) {
                mFlag = p2!!.index
                refreshData()
            }

            override fun onItemLongClick(p0: ViewGroup?, p1: View?, p2: ColorBean?, p3: Int): Boolean {
                return false
            }

        })

        recycler_view?.adapter = mAdapter
        recycler_view?.layoutManager = OverLayCardLayoutManager()
        CardConfig.initConfig(this)
        val callback = RenRenCallback(recycler_view, mAdapter, mDatas)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
        refreshData()

    }

    /**
     * 刷新选择
     */
    private fun refreshData(){
        for (i in mDatas.indices){
            mDatas[i].isChoose = mDatas[i].index == mFlag
        }
        mAdapter?.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_all_back ->{
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        SpUtil.setInt("color_view",mFlag)
        EventBus.getDefault().post(RefreshEvent("colorview",mFlag))
    }

}