package com.zs.project.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.greendao.*
import com.zs.project.listener.ItemClickListener
import com.zs.project.listener.ItemLongClickListener
import com.zs.project.ui.activity.ImageShowActivity
import com.zs.project.ui.activity.WebViewActivity
import com.zs.project.ui.adapter.ArticleAdapter
import com.zs.project.ui.adapter.DouBanAdapter
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
About: 我的收藏
—————————————————————————————————————
 */
class CollectionFragment : BaseFragment() , ItemClickListener , ItemLongClickListener{

    var mType : String ?= null
    private var mNewData :MutableList<ArticleData> = arrayListOf()
    private var mMovieData :MutableList<MovieDetailData> = arrayListOf()
//    private var mAdapter : CollectListAdapter ?= null
    private var mNewAdapter : ArticleAdapter ?= null
    private var mMovieAdapter : DouBanAdapter ?= null

    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.public_list_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mType = arguments?.getString(PublicFieldUtil.TYPE)
        initData()
    }

    override fun initData() {
        super.initData()
        if("news" == mType){
            mNewData = getNewDao().loadAll()
            if (mNewData == null || mNewData!!.size == 0){
                multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
            }else{
                multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                mNewAdapter = ArticleAdapter(context, this , this)
                RecyclerViewUtil.init(activity,recycler_view,mNewAdapter)
                mNewAdapter?.updateData(mNewData)
            }

        }else if("movies" == mType){
            mMovieData = getMovieDao().loadAll()
            if (mMovieData == null || mMovieData!!.size == 0){
                multistate_view?.viewState = MultiStateView.VIEW_STATE_EMPTY
            }else{
                multistate_view?.viewState = MultiStateView.VIEW_STATE_CONTENT
                mMovieAdapter = DouBanAdapter(mMovieData , this , this)
                RecyclerViewUtil.init(activity,recycler_view,mMovieAdapter)
            }
        }
        recycler_view?.setPullRefreshEnabled(false)

    }

    override fun onItemClick(position: Int, data: Any, view: View) {
        if ("news" == mType){
            when (view.id) {
                R.id.iv_article_img , R.id.cl_image_item ->{
                    val intent = Intent(activity, ImageShowActivity::class.java)
                    intent.putExtra(PublicFieldUtil.URL_FIELD, data as String)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            activity as Activity,
                            view.findViewById(view.id),
                            getString(R.string.transition_image)
                    )
                    ActivityCompat.startActivity(activity as Activity, intent, optionsCompat.toBundle())
                }
            }
        } else if("movies" == mType){
            var url = (data as MovieDetailData).alt
            activity?.startActivity<WebViewActivity>("url" to url)
        }
    }

    override fun onItemLongClick(position: Int, data: Any, view: View) {
        SnackbarUtils.Long(multistate_view!!,"删除收藏~")
                .setAction("确定", View.OnClickListener {
                    if ("news" == mType){
                        getNewDao().delete(data as ArticleData)
                        mNewAdapter?.deleteData(position)
                    }else{
                        getMovieDao().delete(data as MovieDetailData)
                        mMovieAdapter?.deleteData(position)
                    }
                    activity?.toast("删除成功")
                })
                .actionColor(Color.parseColor("#ffffff"))
                .danger()
                .show()
    }

    private fun getNewDao() : ArticleDataDao{
        return GreenDaoManager.getInstance().session.articleDataDao
    }

    private fun getMovieDao() : MovieDetailDataDao{
        return GreenDaoManager.getInstance().session.movieDetailDataDao
    }

}