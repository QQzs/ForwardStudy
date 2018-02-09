package com.zs.project.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant.iconNames
import com.zs.project.base.BaseActivity
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.SpUtil
import kotlinx.android.synthetic.main.activity_setting_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*
import org.jetbrains.anko.startActivity

/**
 *
Created by zs
Date：2018年 02月 07日
Time：14:48
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class SettingActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_setting_layout)
    }

    override fun init() {

        tv_all_title?.text = "设置"
        item_color_view?.setOnClickListener(this)
        item_cache_clear?.setOnClickListener(this)

    }

    override fun initData() {

        tv_setting_cache_size?.text = ImageLoaderUtil.getCacheSize(this)

    }

    override fun onResume() {
        super.onResume()
        tv_setting_color_view?.text = iconNames[SpUtil.getInt("color_view",0)]

    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.item_color_view ->{
                this.startActivity<IconChooseActivity>()
            }
            R.id.item_cache_clear ->{
                ImageLoaderUtil.clearImageAllCache(this)
                tv_setting_cache_size?.text = "0K"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        when(requestCode){
            1000 ->{

            }
        }
    }


}