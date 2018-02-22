package com.zs.project.ui.fragment

import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.greendao.*
import com.zs.project.ui.adapter.CollectListAdapter
import com.zs.project.util.PublicFieldUtil
import com.zs.project.view.MultiStateView
import kotlinx.android.synthetic.main.public_list_layout.*
import org.jetbrains.anko.toast

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

    var mType : String ?= null
    private var mNewData :MutableList<NewData> ?= null
    private var mMovieData :MutableList<MovieData> ?= null
    private var mAdapter : CollectListAdapter ?= null

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.public_list_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mType = arguments?.getString(PublicFieldUtil.TYPE)
        activity?.toast("type = " + mType)
        initView()
        initData()

    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
        if("news" == mType){
            mNewData = getNewDao().loadAll()
            if (mNewData == null || mNewData!!.size == 0){
                multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
            }else{
//                mAdapter = CollectListAdapter("news",this)
            }

        }else{
            mMovieData = getMovieDao().loadAll()
            if (mMovieData == null || mMovieData!!.size == 0){
                multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
            }else{

            }
        }

    }

    private fun getNewDao() : NewDataDao{
        return GreenDaoManager.getInstance().session.newDataDao
    }

    private fun getMovieDao() : MovieDataDao{
        return GreenDaoManager.getInstance().session.movieDataDao
    }

}