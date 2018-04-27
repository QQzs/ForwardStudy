package com.zs.project.ui.fragment

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.listener.ItemClickListener

/**
 *
Created by zs
Date：2018年 04月 27日
Time：17:31
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticleFragment : BaseFragment() , ItemClickListener {


    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)

        setContentView(R.layout.fragment_home_layout)

    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }


    override fun onItemClick(position: Int, data: Any, view: View) {


    }

}