package com.zs.project.view.viewattr

import android.support.v4.content.ContextCompat
import android.view.View
import com.zs.project.R
import com.zs.project.app.MyApp
import com.zs.project.view.topscorllview.indicator.ScrollIndicatorView
import com.zs.project.view.topscorllview.indicator.transition.OnTransitionTextListener
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

class AttrScrollIndicator : SkinAttr() {
    override fun applySkin(view: View) {
        if (view is ScrollIndicatorView) {
            var indicatorView = view
            if (isColor) {
                val color = SkinResourcesUtils.getColor(attrValueRefId)
                indicatorView.onTransitionListener = OnTransitionTextListener().setColor(color
                        , ContextCompat.getColor(MyApp.getAppContext(), R.color.font_default))
            }

        }

    }
}
