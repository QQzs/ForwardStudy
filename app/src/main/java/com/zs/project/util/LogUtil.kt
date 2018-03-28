package com.zs.project.util

import android.util.Log

/**
 *
Created by zs
Date：2018年 03月 28日
Time：9:15
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LogUtil{

    companion object{

        val LOG_TAG = "My_Log"

        fun logShow(content : String){
            Log.d(LOG_TAG,content)
        }

    }



}