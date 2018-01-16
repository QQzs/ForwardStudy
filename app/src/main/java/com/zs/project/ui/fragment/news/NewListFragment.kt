package com.zs.project.ui.fragment.news

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.base.LazyFragmentKotlin
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
class NewListFragment : LazyFragmentKotlin(){

    var mIndex : Int = -1
    var mTitleName : String ?= null
    var mTitleCode : String ?= null

    companion object {
        fun getInstance(vararg arg: String) : NewListFragment{
            var fragment = NewListFragment()
            if (arg.size != 0){
                var bundle = Bundle()
                if (arg.size > 0){
                    bundle.putString("index",arg[0])
                }
                if (arg.size > 1){
                   bundle.putString("name",arg[1])
                }
                if (arg.size > 2){
                    bundle.putString("code",arg[2])
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
        if (mIndex > 0){
            initView()
            Log.d("My_Log","onCreateViewLazy  name = " + mTitleName)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mIndex == 0){
            initView()
            Log.d("My_Log","Created name = " + mTitleName)
        }
    }

    override fun initData() {
        super.initData()
        mIndex = arguments.getString("index").toInt()
        mTitleName = arguments.getString("name")
        mTitleCode = arguments.getString("code")
    }

    override fun initView() {
        super.initView()
        tv_content?.text = mTitleName
    }


}