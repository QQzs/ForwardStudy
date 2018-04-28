package com.zs.project.view.viewattr

import android.view.View
import com.zs.project.view.SwitchColorView
import solid.ren.skinlibrary.attr.base.SkinAttr
import solid.ren.skinlibrary.utils.SkinResourcesUtils

/**
 * Created by zs
 * Date：2018年 03月 20日
 * Time：13:30
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class AttrSwitchColorView : SkinAttr() {
    override fun applySkin(view: View) {
        if (view is SwitchColorView) {
            var switchColorView = view
            if (isColor) {
                val color = SkinResourcesUtils.getColor(attrValueRefId)
                switchColorView.switchColor(color)
            }

        }

    }
}
