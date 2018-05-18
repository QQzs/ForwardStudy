package com.zs.project.ui.activity.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseActivity
import com.zs.project.event.LoginEvent
import com.zs.project.event.RefreshEvent
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.SpUtil
import com.zs.project.util.StringUtils
import kotlinx.android.synthetic.main.activity_setting_layout.*
import kotlinx.android.synthetic.main.public_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *
Created by zs
Date：2018年 02月 07日
Time：14:48
—————————————————————————————————————
About: 设置页面
—————————————————————————————————————
 */
class SettingActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_setting_layout)
        EventBus.getDefault().register(this)
    }

    override fun init() {

        tv_all_title?.text = "设置"
        iv_all_back?.setOnClickListener(this)
        item_color_view?.setOnClickListener(this)
        item_cache_clear?.setOnClickListener(this)
        tv_exit_view?.setOnClickListener(this)

        dynamicAddView(tv_exit_view , "contentColor" , R.color.app_main_color)

    }

    override fun initData() {

        tv_setting_cache_size?.text = ImageLoaderUtil.getCacheSize(this)
        tv_setting_color_view?.text = Constant.iconNames[SpUtil.getInt("color_view",0)]

    }

    override fun onResume() {
        super.onResume()
        Log.d("My_Log","position = " + SpUtil.getInt("color_view",0))
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_all_back -> finish()
            R.id.item_color_view ->{
                this.startActivity<IconChooseActivity>()
            }
            R.id.item_cache_clear ->{
                ImageLoaderUtil.clearImageAllCache(this)
                tv_setting_cache_size?.text = "0K"
            }
            R.id.tv_exit_view ->{
                var userId = SpUtil.getString(Constant.APP_USER_ID,"")
                var userName = SpUtil.getString(Constant.APP_USER_NAME,"")
                if (StringUtils.isNullOrEmpty(userId)){
                    toast("还未登录~")
                }else{
//                    mDialogUtil?.showNoticeDialog("","您确定要退出账号？")
//                    mDialogUtil?.setDialogNoticeListener(object : DialogUtil.DialogNoticeListener{
//                        override fun onComfirmClick(dialog: Dialog) {
//                            dialog.dismiss()
//                            SpUtil.clearAll()
//                            SpUtil.setString(Constant.APP_USER_NAME,userName)
//                            EventBus.getDefault().post(LoginEvent("" , userName))
//                            EventBus.getDefault().post(RefreshEvent("article" , true))
//                            finish()
//                        }
//
//                        override fun onCancelClick(dialog: Dialog) {
//                            dialog.dismiss()
//                        }
//
//                    })

                    AlertDialog.Builder(this)
                            .setCancelable(true)
                            .setMessage("确定退出当前账号？")
                            .setPositiveButton("确定") {
                                dialog, which ->
                                dialog.dismiss()
                                SpUtil.clearAll()
                                SpUtil.setString(Constant.APP_USER_NAME,userName)
                                EventBus.getDefault().post(LoginEvent("" , userName))
                                EventBus.getDefault().post(RefreshEvent("article" , true))
                                finish()
                            }
                            .setNegativeButton("取消") {
                                dialog, which -> dialog.dismiss()
                            }
                            .create()
                            .show()

                }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleScroll(event: RefreshEvent?) {
        if (event == null){
            return
        }
        if ("colorview" == event.flag){
            tv_setting_color_view?.text = Constant.iconNames[event.refresh_int]
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}