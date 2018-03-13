package com.zs.project.ui.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.app.Constant.iconId
import com.zs.project.app.Constant.iconNames
import com.zs.project.base.BaseActivity
import com.zs.project.bean.ColorBean
import com.zs.project.event.RefreshEvent
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.SpUtil
import kotlinx.android.synthetic.main.activity_icon_choose_layout.*
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
//    var mAdapter : ColorViewAdapter ?= null
    var mDatas: MutableList<ColorBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_icon_choose_layout)
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

                viewHolder?.setImageResource(R.id.iv_choose_check,if (colorBean!!.isChoose){
                    R.mipmap.item_choose_sel
                }else{
                    R.mipmap.item_choose_nor
                })

                viewHolder?.setOnClickListener(R.id.cv_color_item , {
                    mFlag = colorBean!!.index
                    refreshData()
                })

            }

        }
//        color_sel_recycler?.adapter = mAdapter
//        color_sel_recycler?.layoutManager = OverLayCardLayoutManager()
//        CardConfig.initConfig(this)
//        val callback = RenRenCallback(color_sel_recycler, mAdapter, mDatas)
//        val itemTouchHelper = ItemTouchHelper(callback)
//        itemTouchHelper.attachToRecyclerView(color_sel_recycler)

        RecyclerViewUtil.initNoDecoration(this,color_sel_recycler,mAdapter)
        refreshData()

    }

    /**
     * 刷新选择
     */
    private fun refreshData(){
        for (i in mDatas.indices){
            mDatas[i].isChoose = i == mFlag
        }
        // 为了有点击效果，延迟一会
        Handler().postDelayed({
            mAdapter?.notifyDataSetChanged()
        },200)

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
        Log.d("My_Log","onStop")
        SpUtil.setInt("color_view",mFlag)
        EventBus.getDefault().post(RefreshEvent("colorview",mFlag))
    }

}