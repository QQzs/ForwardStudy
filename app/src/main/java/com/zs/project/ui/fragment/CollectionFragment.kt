package com.zs.project.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.greendao.*
import com.zs.project.listener.ItemClickListener
import com.zs.project.listener.ItemLongClickListener
import com.zs.project.ui.activity.WebViewActivity
import com.zs.project.ui.adapter.CollectListAdapter
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.SnackbarUtils
import com.zs.project.view.MultiStateView
import kotlinx.android.synthetic.main.public_list_layout.*
import org.jetbrains.anko.startActivity
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
class CollectionFragment : BaseFragment() , ItemClickListener , ItemLongClickListener{

    var mType : String ?= null
    private var mNewData :MutableList<NewData> = arrayListOf()
    private var mMovieData :MutableList<MovieData> = arrayListOf()
    private var mAdapter : CollectListAdapter ?= null

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.public_list_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mType = arguments?.getString(PublicFieldUtil.TYPE)
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
                multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                mAdapter = CollectListAdapter("news",this,this)
                RecyclerViewUtil.init(activity,recycler_view,mAdapter)
                mAdapter?.updateNewData(mNewData)

            }

        }else{
            mMovieData = getMovieDao().loadAll()
            if (mMovieData == null || mMovieData!!.size == 0){
                multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
            }else{
                multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                mAdapter = CollectListAdapter("movies",this,this)
                RecyclerViewUtil.initNoDecoration(activity,recycler_view,mAdapter)
                mAdapter?.updateMovieData(mMovieData)
            }
        }
        recycler_view?.setPullRefreshEnabled(false)

    }

    override fun onItemClick(position: Int, data: Any, view: View) {
        if ("news" == mType){
            var url = (data as NewData).weburl
            activity!!.startActivity<WebViewActivity>("url" to url)
        }else{
            var url = (data as MovieData).url
            activity?.startActivity<WebViewActivity>("url" to url)
        }
    }

    override fun onItemLongClick(position: Int, data: Any, view: View) {
        SnackbarUtils.Long(multistate_view!!,"删除收藏~")
                .setAction("确定", View.OnClickListener {
                    if ("news" == mType){
                        getNewDao().delete(data as NewData)
                    }else{
                        getMovieDao().delete(data as MovieData)
                    }
                    mAdapter?.deleteData(position)
                    activity?.toast("删除成功")
                })
                .actionColor(Color.parseColor("#ffffff"))
                .danger()
                .show()
    }

    private fun getNewDao() : NewDataDao{
        return GreenDaoManager.getInstance().session.newDataDao
    }

    private fun getMovieDao() : MovieDataDao{
        return GreenDaoManager.getInstance().session.movieDataDao
    }

}