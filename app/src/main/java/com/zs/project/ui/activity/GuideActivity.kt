package com.zs.project.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import org.jetbrains.anko.startActivity

/**
 *
Created by zs
Date：2018年 02月 07日
Time：11:50
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class GuideActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
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