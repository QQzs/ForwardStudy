package com.zs.project.ui.activity

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*


/**
 *
Created by zs
Date：2018年 02月 09日
Time：16:34
—————————————————————————————————————
About:关于页面
—————————————————————————————————————
 */
class AboutActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_about_layout)
    }

    override fun init() {
        tv_all_title?.text = "关于"
        iv_all_back?.setOnClickListener(this)
    }

    override fun initData() {
        tv_app_version?.text = getLocalVersion(this)
    }

    fun getLocalVersion(ctx: Context): String {
        var localName = "2"
        try {
            val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.packageName, 0)
            localName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localName
    }


    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_all_back ->{
                finish()
            }
        }

    }

}
