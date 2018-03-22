package com.zs.project.app;

import com.zs.project.util.SpUtil;
import com.zs.project.view.viewattr.AttrScrollColorBar;
import com.zs.project.view.viewattr.AttrScrollIndicator;
import com.zs.project.view.viewattr.AttrSnackbar;

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

public class MyApp extends SkinBaseApplication {

    public static MyApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        SpUtil.init(this,"zs_data");
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.enableGlobalSkinApply();
        SkinConfig.addSupportAttr("colorBar" , new AttrScrollColorBar());
        SkinConfig.addSupportAttr("scrollIndicator" , new AttrScrollIndicator());
        SkinConfig.addSupportAttr("snackBar" , new AttrSnackbar());

    }

    public static MyApp getAppContext(){
        return mApplication;
    }


}
