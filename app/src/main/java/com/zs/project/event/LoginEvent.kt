package com.zs.project.event

/**
 *
Created by zs
Date：2018年 05月 03日
Time：9:25
—————————————————————————————————————
About: 登录  退出  事件
—————————————————————————————————————
 */
class LoginEvent{

    /**
     *  1 : 登录   0 : 退出
     */
    var action : String = ""
    var userId : String = ""
    var userName : String = ""

    constructor(userId: String, userName: String) {
        this.userId = userId
        this.userName = userName
    }
}