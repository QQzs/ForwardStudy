package com.zs.project.ui.activity

import android.os.Bundle
import android.view.View
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
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
About:
—————————————————————————————————————
 */
class IconChooseActivity : BaseActivity(){

    var mFlag : Int = 0
    var mAdapter: CommonAdapter<ColorBean>?= null
    var mDatas: MutableList<ColorBean> = ArrayList()

    val mImages = intArrayOf(R.mipmap.ic_snow_img, R.mipmap.ic_star_img, R.mipmap.ic_heart_img)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_icon_choose_layout)
    }

    override fun init() {

        tv_all_title?.text = "选择图标"
        tv_all_right?.text = "保存"
        iv_all_back?.setOnClickListener(this)
        tv_all_right?.setOnClickListener(this)

    }

    override fun initData() {

        mDatas.add(ColorBean(0 , iconNames[0]))
        mDatas.add(ColorBean(1 , iconNames[1]))
        mDatas.add(ColorBean(2 , iconNames[2]))

        mFlag = SpUtil.getInt("color_view",0)
        mAdapter = object : CommonAdapter<ColorBean>(this,mDatas,R.layout.item_color_choose_layout){

            override fun convert(viewHolder: ViewHolder?, colorBean: ColorBean?) {

                viewHolder?.setImageResource(R.id.iv_icon_img,mImages[colorBean!!.index])
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
        RecyclerViewUtil.init(this,color_sel_recycler,mAdapter)
        refreshData()
//        color_sel_recycler?.adapter = mAdapter
//        color_sel_recycler?.layoutManager = OverLayCardLayoutManager()
//        CardConfig.initConfig(this)
//        val callback = RenRenCallback(color_sel_recycler, mAdapter, mDatas)
//        val itemTouchHelper = ItemTouchHelper(callback)
//        itemTouchHelper.attachToRecyclerView(color_sel_recycler)

    }

    /**
     * 刷新选择
     */
    private fun refreshData(){
        for (i in mDatas.indices){
            mDatas[i].isChoose = i == mFlag
        }
        mAdapter?.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_all_back ->{
                finish()
            }
            R.id.tv_all_right ->{
                SpUtil.setInt("color_view",mFlag)
                EventBus.getDefault().post(RefreshEvent("colorview",mFlag))
                finish()
            }
        }
    }

}