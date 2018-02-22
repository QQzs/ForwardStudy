package com.zs.project.ui.activity

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
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
About:图片预览
—————————————————————————————————————
 */
class ImageShowActivity : BaseActivity(){

    var mImageUrl : String ?= null
    var scale: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_image_layout)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    override fun init() {
    }

    override fun initData() {

        mImageUrl = intent.getStringExtra(PublicFieldUtil.URL_FIELD)
        //创建一个photoview的一个attacher
        ImageLoaderUtil.displayImage(mImageUrl,iv_show_img)
    }

    private fun displayImage() {
        //想让图片宽是屏幕的宽度
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.default_img)
        //测量
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true//只测量
        val height = bitmap.height
        val width = bitmap.width
        //再拿到屏幕的宽
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val screenWidth = display.width
        //计算如果让照片是屏幕的宽，选要乘以多少？
        scale = screenWidth / width
        //这个时候。只需让图片的宽是屏幕的宽，高乘以比例
        val displayHeight = height * scale//要显示的高，这样避免失真
        //最终让图片按照宽是屏幕 高是等比例缩放的大小
        val layoutParams = RelativeLayout.LayoutParams(screenWidth, displayHeight)
        iv_show_img.layoutParams = layoutParams
    }


    override fun onClick(p0: View?) {
    }
}