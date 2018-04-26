package com.zs.project.ui.activity

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.bean.LoginBean
import com.zs.project.request.RequestHelper
import com.zs.project.request.bean.BaseResponseAndroid
import com.zs.project.request.cookie.DefaultObserverAndroid
import com.zs.project.util.StringUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login_layout.*

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_login_layout)
    }

    override fun init() {

        tv_login?.setOnClickListener(this)

    }



    override fun initData() {

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_login ->{
                var name = text_input_name?.editText?.text.toString()
                var passWord = text_input_password?.editText?.text.toString()

                if (StringUtils.isNullOrEmpty(name)){
                    text_input_name?.error = "请填写用户名"
                    return
                }else{
                    text_input_name?.isErrorEnabled = false
                }

                if (StringUtils.isNullOrEmpty(passWord)){
                    text_input_name?.error = "请填写密码"
                    return
                }else{
                    text_input_name?.isErrorEnabled = false
                }
            }

        }


    }


    override fun requestData(request: Observable<*>?, type: Int) {
        super.requestData(request, type)
        var observable = RequestHelper.getObservable(request)
        observable.subscribe(object : DefaultObserverAndroid<BaseResponseAndroid<LoginBean>>(this){
            override fun onSuccess(response: BaseResponseAndroid<LoginBean>?) {

                var login = response?.data

            }

        })
    }

}