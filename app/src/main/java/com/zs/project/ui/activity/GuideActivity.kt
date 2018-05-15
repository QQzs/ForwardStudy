package com.zs.project.ui.activity

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zs.project.R
import com.zs.project.app.AppStatusManager
import com.zs.project.base.BaseActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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

    var mPermissions : RxPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppStatusManager.getInstance().setAppStatus(AppStatusManager.STATUS_NORMAL)
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_guide_layout)
        getPermissions()

    }

    override fun init() {

    }

    override fun initData() {

        Handler().postDelayed({
            this.startActivity<MainActivity>()
            finish()
        },500)

    }

    /**
     * 获取读写权限
     */
    private fun getPermissions(){
        mPermissions = RxPermissions(this)
        mPermissions?.request(Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
                ?.subscribe({
                    if (it){

                    }else{
                        toast("读取存储卡权限已关闭")
                    }
                })
    }

    override fun onClick(view: View?) {

    }

}