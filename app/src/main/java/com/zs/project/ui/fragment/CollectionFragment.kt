package com.zs.project.ui.fragment

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.view.MultiStateView
import kotlinx.android.synthetic.main.public_list_layout.*

/**
 *
Created by zs
Date：2018年 02月 09日
Time：16:57
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class CollectionFragment : BaseFragment(){

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.public_list_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    override fun initView() {
        super.initView()
        multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun initData() {
        super.initData()
    }

}