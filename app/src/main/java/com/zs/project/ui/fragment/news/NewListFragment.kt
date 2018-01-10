package com.zs.project.ui.fragment.news

import android.os.Bundle
import com.zs.project.R
import com.zs.project.ui.fragment.LazyFragment

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

    var mIndex : String ?= null

    companion object {
        fun newInstance(vararg arg: String){
            var fragment = NewListFragment()
            if (arg.size != 0){
                var bundle = Bundle()
                if (arg.size > 0){
                   bundle.putString("index",arg[0])
                }
                fragment.arguments = bundle
            }
        }
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        setContentView(R.layout.fragment_new_list_layout)
        mIndex = arguments.getString("index")
    }

}