package com.zs.project.view.viewattr

import android.view.View
import com.zs.project.util.SnackbarUtils
import com.zs.project.view.MultiStateView
import solid.ren.skinlibrary.attr.base.SkinAttr
import solid.ren.skinlibrary.utils.SkinResourcesUtils

/**
 *
Created by zs
Date：2018年 03月 20日
Time：18:34
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class AttrSnackbar : SkinAttr(){
    override fun applySkin(view: View?) {

        if(view is MultiStateView){
            if (isColor){
                SnackbarUtils.setDefaultColor(SkinResourcesUtils.getColor(attrValueRefId))
            }
        }

    }

}
