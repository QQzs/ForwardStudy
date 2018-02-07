package com.zs.project.ui.activity

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.event.RefreshEvent
import com.zs.project.util.ImageLoaderUtil
import kotlinx.android.synthetic.main.activity_setting_layout.*
import org.greenrobot.eventbus.EventBus

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

    var mFlag : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_setting_layout)
    }

    override fun init() {

        item_color_view?.setOnClickListener(this)
        item_cache_clear?.setOnClickListener(this)

    }

    override fun initData() {

        tv_setting_cache_size?.text = ImageLoaderUtil.getCacheSize(this)

    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.item_color_view ->{

                if (mFlag == 2){
                    mFlag = 0
                }else{
                    mFlag ++
                }
                EventBus.getDefault().post(RefreshEvent("colorview",mFlag))
            }
            R.id.item_cache_clear ->{
                ImageLoaderUtil.clearImageAllCache(this)
            }
        }
    }


}