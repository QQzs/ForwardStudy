package com.zs.project.ui.fragment

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment

/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class DouBanFragment : BaseFragment() {

    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_home_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
