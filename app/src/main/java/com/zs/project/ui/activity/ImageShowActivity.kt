package com.zs.project.ui.activity

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.PublicFieldUtil
import kotlinx.android.synthetic.main.activity_image_layout.*

/**
 *
Created by zs
Date：2018年 02月 09日
Time：17:25
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ImageShowActivity : BaseActivity(){

    var mImageUrl : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_image_layout)
    }

    override fun init() {
    }

    override fun initData() {

        mImageUrl = intent.getStringExtra(PublicFieldUtil.URL_FIELD)
        ImageLoaderUtil.displayImage(mImageUrl,iv_show_img)

    }

    override fun onClick(p0: View?) {
    }
}