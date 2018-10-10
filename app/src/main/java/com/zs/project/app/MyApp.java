package com.zs.project.app;

import com.squareup.leakcanary.LeakCanary;
import com.zs.project.util.SpUtil;
import com.zs.project.view.viewattr.AttrBorderView;
import com.zs.project.view.viewattr.AttrScrollColorBar;
import com.zs.project.view.viewattr.AttrScrollIndicator;
import com.zs.project.view.viewattr.AttrSnackbar;
import com.zs.project.view.viewattr.AttrSwitchColorView;

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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // 这个线程是专门给LeakCanary做堆内存分析的
            // 在这里不要写app初始化代码
            return;
        }
        LeakCanary.install(this);

        mApplication = this;
        SpUtil.init(this,"zs_data");
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.enableGlobalSkinApply();
        SkinConfig.addSupportAttr("colorBar" , new AttrScrollColorBar());
        SkinConfig.addSupportAttr("scrollIndicator" , new AttrScrollIndicator());
        SkinConfig.addSupportAttr("snackBar" , new AttrSnackbar());
        SkinConfig.addSupportAttr("switchColor" , new AttrSwitchColorView());
        SkinConfig.addSupportAttr("contentColor" , new AttrBorderView());

    }

    public static MyApp getAppContext(){
        return mApplication;
    }


}
