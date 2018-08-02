package com.zs.project.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseActivity
import com.zs.project.bean.LoginBean
import com.zs.project.event.LoginEvent
import com.zs.project.event.RefreshEvent
import com.zs.project.request.DefaultObserverAndroid
import com.zs.project.request.RequestApi
import com.zs.project.request.RequestHelper
import com.zs.project.request.bean.BaseResponseAndroid
import com.zs.project.util.LogUtil
import com.zs.project.util.SpUtil
import com.zs.project.util.StringUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast

/**
 *
Created by zs
Date：2018年 03月 22日
Time：16:00
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginActivity : BaseActivity(){

    var mAction : String = "login"

    var LOGIN_ANDROID: Int = 1000
    var REGISTER_ANDROID: Int = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_login_layout)
    }

    override fun init() {

        card_login_view?.setOnClickListener(this)
        tv_login_switch?.setOnClickListener(this)
        dynamicAddView(tv_login_view , "contentColor" , R.color.app_main_color)

    }

    override fun initData() {

        Handler().postDelayed({
            if (!StringUtils.isNullOrEmpty(mUserName)){
                text_input_name?.editText?.setText(mUserName)
                text_input_name?.editText?.setSelection(mUserName.length)
            }
        },300)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.card_login_view ->{
                var name = text_input_name?.editText?.text.toString()
                var passWord = text_input_password?.editText?.text.toString()
                var passWordAgain = text_input_password_again?.editText?.text.toString()

                if (StringUtils.isNullOrEmpty(name)){
                    text_input_name?.error = "请填写用户名"
                    return
                }else{
                    text_input_name?.isErrorEnabled = false
                }

                if (StringUtils.isNullOrEmpty(passWord)){
                    text_input_password?.error = "请填写密码"
                    return
                }else{
                    text_input_password?.isErrorEnabled = false
                }

                if (mAction == "login"){
                    requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).loginAndroid(name,passWord),LOGIN_ANDROID)
                }else{
                    if (StringUtils.isNullOrEmpty(passWordAgain)){
                        text_input_password_again?.error = "请确认密码"
                        return
                    } else if (passWord != passWordAgain){
                        text_input_password_again?.error = "确认密码不正确"
                        return
                    } else{
                        text_input_password_again?.isErrorEnabled = false
                    }
                    requestData(mRequestApi.getRequestService(RequestApi.REQUEST_ANDROID).registerAndroid(name,passWord,passWordAgain),LOGIN_ANDROID)
                }
            }

            R.id.tv_login_switch ->{
                if (mAction == "login"){
                    mAction = "register"
                    text_input_password_again?.visibility = View.VISIBLE
                    tv_login_switch?.text = "登录"
                    tv_login_view?.text = "注册"

                }else{
                    mAction = "login"
                    text_input_password_again?.editText?.setText("")
                    text_input_password_again?.visibility = View.GONE
                    tv_login_switch?.text = "注册"
                    tv_login_view?.text = "登录"
                }
            }

        }


    }

    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestHelper.getObservable(request)
        observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<LoginBean>>(this){
            override fun onSuccess(response: BaseResponseAndroid<LoginBean>?) {
                when(type){
                    LOGIN_ANDROID , REGISTER_ANDROID ->{
                        var user = response?.data
                        if (!StringUtils.isNullOrEmpty(user?.id) && !StringUtils.isNullOrEmpty(user?.username)){
                            SpUtil.setString(Constant.APP_USER_ID,user?.id)
                            SpUtil.setString(Constant.APP_USER_NAME,user?.username)
                            EventBus.getDefault().post(LoginEvent(user!!.id , user!!.username))
                            EventBus.getDefault().post(RefreshEvent("article" , true))
                            finish()
                        }else{
                            toast("用户信息出错！")
                        }
                        LogUtil.logShow("name = " + user?.username + " password = " + user?.password)
                    }

                }
            }

        })
    }

}