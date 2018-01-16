package com.zs.project.ui.fragment

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_me_layout.*

/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class MeFragment : BaseFragment() {

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_me_layout)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun initData() {
        super.initData()
        tv_test?.text = "fffffffffffff"
    }

}
