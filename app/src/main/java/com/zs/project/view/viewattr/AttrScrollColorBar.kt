package com.zs.project.view.viewattr

import android.view.View
import com.zs.project.app.MyApp
import com.zs.project.view.topscorllview.indicator.ScrollIndicatorView
import com.zs.project.view.topscorllview.indicator.slidebar.ColorBar
import com.zs.project.view.topscorllview.indicator.slidebar.ScrollBar
import org.jetbrains.anko.dip
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

class AttrScrollColorBar : SkinAttr() {
    override fun applySkin(view: View) {
        if (view is ScrollIndicatorView) {
            var indicatorView = view
            if (isColor) {
                val color = SkinResourcesUtils.getColor(attrValueRefId)
                var colorBar = ColorBar(MyApp.getAppContext(), color, MyApp.getAppContext().dip(2f), ScrollBar.Gravity.BOTTOM)
                indicatorView.setScrollBar(colorBar)
            }

        }

    }
}
