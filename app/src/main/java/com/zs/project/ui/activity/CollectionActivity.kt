package com.zs.project.ui.activity

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseActivity
import com.zs.project.ui.fragment.CollectionFragment
import com.zs.project.util.PublicFieldUtil
import kotlinx.android.synthetic.main.public_title_layout.*

/**
 *
Created by zs
Date：2018年 02月 09日
Time：16:25
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class CollectionActivity : BaseActivity(){

    var mFlag : String ?= null
    var mFragment : CollectionFragment = CollectionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_collection_layout)
    }

    override fun init() {

        mFlag = intent.getStringExtra(PublicFieldUtil.FLAG_FIELD)
        if ("news" == mFlag){
            tv_all_title?.text = "我的新闻"
        }else if ("movies" == mFlag){
            tv_all_title?.text = "我的电影"
        }
        iv_all_back?.setOnClickListener(this)


        var bundle = Bundle()
        bundle.putString(PublicFieldUtil.TYPE,mFlag)
        mFragment.arguments = bundle

        var fragmentManager = supportFragmentManager
        var fragmenttransaction = fragmentManager?.beginTransaction()
        fragmenttransaction?.add(R.id.fl_data_content , mFragment)
        fragmenttransaction?.commit()
    }

    override fun initData() {


    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_all_back ->{
                finish()
            }
        }

    }
}