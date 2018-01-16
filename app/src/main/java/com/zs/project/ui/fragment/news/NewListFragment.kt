package com.zs.project.ui.fragment.news

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.base.LazyFragment
import kotlinx.android.synthetic.main.fragment_new_list_layout.*

/**
 *
Created by zs
Date：2018年 01月 05日
Time：14:49
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class NewListFragment : LazyFragment(){

    var mTitleName : String ?= null
    var mTitleCode : String ?= null

    companion object {
        fun getInstance(vararg arg: String) : NewListFragment{
            var fragment = NewListFragment()
            if (arg.size != 0){
                var bundle = Bundle()
                if (arg.size > 0){
                   bundle.putString("name",arg[0])
                }
                if (arg.size > 1){
                    bundle.putString("code",arg[1])
                }
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        setContentView(R.layout.fragment_new_list_layout)
        initData()
        initView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initData() {
        super.initData()
        mTitleName = arguments.getString("name")
        mTitleCode = arguments.getString("code")

        Log.d("My_Log","onCreateViewLazy  name = " + mTitleName)
    }

    override fun initView() {
        super.initView()
        tv_content?.text = mTitleName
    }


}