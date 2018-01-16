package com.zs.project.app;

import android.app.Application;

import com.zs.project.util.SharedPreferencesUtil;

/**
 * Created by zs
 * Date：2018年 01月 02日
 * Time：17:52
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class MyApplication extends Application {

    public static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        SharedPreferencesUtil.init(this,"zs_data");
    }

    public static MyApplication getInstance(){
        return mApplication;
    }


}
