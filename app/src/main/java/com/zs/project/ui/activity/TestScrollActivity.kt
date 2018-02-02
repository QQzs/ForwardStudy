package com.zs.project.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.project.R
import com.zs.project.ui.adapter.MeAdapter
import com.zs.project.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.activity_test_scroll.*

/**
 *
Created by zs
Date：2018年 02月 01日
Time：17:00
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TestScrollActivity : AppCompatActivity(){


    var mAapter : MeAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_scroll)

        mAapter = MeAdapter()
        RecyclerViewUtil.init(this,scroll_view_pull,mAapter)
        scroll_view_pull?.setPullRefreshEnabled(false)

        scroll_view_pull?.headerImageView?.setImageResource(R.mipmap.default_img)

    }


}