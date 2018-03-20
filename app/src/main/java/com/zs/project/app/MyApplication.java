package com.zs.project.app;

import com.zs.project.util.SpUtil;
import com.zs.project.view.viewattr.AttrScrollColorBar;
import com.zs.project.view.viewattr.AttrScrollIndicator;

import solid.ren.skinlibrary.SkinConfig;
import solid.ren.skinlibrary.base.SkinBaseApplication;

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
        SkinConfig.enableGlobalSkinApply();
        SkinConfig.addSupportAttr("colorBar" , new AttrScrollColorBar());
        SkinConfig.addSupportAttr("scrollIndicator" , new AttrScrollIndicator());
    }

    public static MyApplication getAppContext(){
        return mApplication;
    }


}
