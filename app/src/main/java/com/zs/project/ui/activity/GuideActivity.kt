package com.zs.project.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.zs.project.R
import com.zs.project.app.AppStatusManager
import com.zs.project.base.BaseActivity
import org.jetbrains.anko.startActivity

/**
 *
Created by zs
Date：2018年 02月 07日
Time：11:50
—————————————————————————————————————
About: 引导页面
—————————————————————————————————————
 */
class GuideActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        AppStatusManager.getInstance().setAppStatus(AppStatusManager.STATUS_NORMAL)
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_guide_layout)
    }

    override fun init() {

    }

    override fun initData() {

        Handler().postDelayed({
            this.startActivity<MainActivity>()
            finish()
        },500)

    }

    override fun onClick(p0: View?) {

    }

}