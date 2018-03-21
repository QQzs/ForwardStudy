package com.zs.project.ui.activity.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.bean.SkinBean
import com.zs.project.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.public_list_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*
import solid.ren.skinlibrary.SkinLoaderListener
import solid.ren.skinlibrary.loader.SkinManager

/**
 *
Created by zs
Date：2018年 03月 20日
Time：14:45
—————————————————————————————————————
About: 设置主题皮肤
—————————————————————————————————————
 */
class SkinChangeActivity : BaseActivity(){

    var mAdapter: CommonAdapter<SkinBean>? = null
    var mData = mutableListOf<SkinBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.public_list_activity_layout)
    }

    override fun init() {

        tv_all_title?.text = "设置皮肤"
        iv_all_back?.setOnClickListener(this)

    }

    override fun initData() {

        mData.add(SkinBean("红色记忆" , R.color.rect))
        mData.add(SkinBean("绿色心情" , R.color.triangle))
        mData.add(SkinBean("蓝色海洋" , R.color.circle))

        mAdapter = object : CommonAdapter<SkinBean>(this, mData , R.layout.item_skin_layout){
            override fun convert(viewHolder: ViewHolder?, skinbean: SkinBean?) {

                viewHolder?.setText(R.id.tv_skin_name,skinbean?.name)
                viewHolder?.setBackgroundRes(R.id.view_skin_color,skinbean?.colorId!!)
                viewHolder?.setOnClickListener(R.id.cv_skin_item,{
                    var index = viewHolder.layoutPosition
                    when(index){
                        1 -> SkinManager.getInstance().restoreDefaultTheme()
                        2 -> changeSkin("theme-green-1.skin")
                        3 -> changeSkin("theme-blue-1.skin")
                    }

                })

            }

        }
        recycler_view?.setPullRefreshEnabled(false)
        RecyclerViewUtil.initGrid(this,recycler_view,mAdapter,2)
    }

    private fun changeSkin(theme : String){
        SkinManager.getInstance().loadSkin(theme,
                object : SkinLoaderListener {
                    override fun onStart() {
                        Log.i("SkinLoaderListener", "正在切换中")
                        //dialog.show();
                    }

                    override fun onSuccess() {
                        Log.i("SkinLoaderListener", "切换成功")
                    }

                    override fun onFailed(errMsg: String) {
                        Log.i("SkinLoaderListener", "切换失败:" + errMsg)
                    }

                    override fun onProgress(progress: Int) {
                        Log.i("SkinLoaderListener", "皮肤文件下载中:" + progress)

                    }
                }

        )

    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_all_back -> finish()
        }
    }

}
