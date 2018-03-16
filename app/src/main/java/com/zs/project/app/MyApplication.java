package com.zs.project.app;

import com.zs.project.util.SpUtil;

import solid.ren.skinlibrary.base.SkinBaseApplication;
import solid.ren.skinlibrary.config.SkinConfig;

/**
 * Created by zs
 * Date：2018年 01月 02日
 * Time：17:52
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class MyApplication extends SkinBaseApplication {

    public static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        SpUtil.init(this,"zs_data");
        SkinConfig.setCanChangeStatusColor(true);
    }

    public static MyApplication getAppContext(){
        return mApplication;
    }


}
