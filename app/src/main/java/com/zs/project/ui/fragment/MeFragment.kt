package com.zs.project.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.bean.ItemBean
import com.zs.project.listener.KotlinItemClickListener
import com.zs.project.request.RequestApi
import com.zs.project.ui.activity.AboutActivity
import com.zs.project.ui.activity.CollectionActivity
import com.zs.project.ui.activity.SettingActivity
import com.zs.project.ui.adapter.MeItemAdapter
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.ScreenUtil
import kotlinx.android.synthetic.main.fragment_me_layout.*
import kotlinx.android.synthetic.main.zoom_header_layout.view.*
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import solid.ren.skinlibrary.listener.ILoaderListener
import solid.ren.skinlibrary.loader.SkinManager


/**
 * Created by zs
 * Date：2018年 01月 04日
 * Time：11:38
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class MeFragment : BaseFragment() , View.OnClickListener , KotlinItemClickListener {
    var mFragment : MeFragment ?= null
    var mFlag : Boolean = true
    var mAdapter : MeItemAdapter?= null
    var mData : MutableList<ItemBean> ?= mutableListOf()
    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_me_layout)
        mFragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun initView() {
        super.initView()
        mData?.add(ItemBean(R.mipmap.ic_me_news,"我的新闻",0))
        mData?.add(ItemBean(R.mipmap.ic_me_movie,"我的电影",1))
        mData?.add(ItemBean(R.mipmap.ic_me_setting,"设置",2))
        mData?.add(ItemBean(R.mipmap.ic_me_about,"关于",3))
        mData?.add(ItemBean(R.mipmap.ic_me_about,"换肤",4))

        mAdapter = MeItemAdapter(mData!!,this)
        RecyclerViewUtil.init(activity,recycler_view_me,mAdapter)
        recycler_view_me?.setPullRefreshEnabled(false)

        var zoomHeader = View.inflate(activity,R.layout.zoom_header_layout,null)
        recycler_view_me?.addZoomHeaderView(zoomHeader, ScreenUtil.dp2px(200f))
        ImageLoaderUtil.loadCircleImage(R.mipmap.default_img,zoomHeader.iv_me_avator)

        ImageLoaderUtil.displayBlurImage(R.drawable.head_bg_img,zoomHeader.iv_zoom_img)

    }

    override fun initData() {
        super.initData()

        var listDataCall = mRequestApi.getRequestService(RequestApi.REQUEST_DOUBAN).getTestData(Constant.MOVIE_THEATERS,0,1)
        listDataCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("My_Log","error")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("My_Log",response?.body()?.string())
            }

        })

    }

    override fun onClick(view: View?) {

        when(view?.id){

        }

    }

    override fun onItemClick(position: Int, data: Any) {

        var bean = data as ItemBean
        when(bean.type){
            0 ->{
                activity?.startActivity<CollectionActivity>(PublicFieldUtil.FLAG_FIELD to "news")
            }
            1 ->{
                activity?.startActivity<CollectionActivity>(PublicFieldUtil.FLAG_FIELD to "movies")
            }
            2 ->{
               activity?.startActivity<SettingActivity>()
            }
            3 ->{
                activity?.startActivity<AboutActivity>()
            }
            4 ->{
                SkinManager.getInstance().loadSkin("theme-green.skin",
                        object : ILoaderListener {
                            override fun onSuccess() {
                                Log.i("SkinLoaderListener", "切换成功")
                            }

                            override fun onFailed(errMsg: String?) {
                                Log.i("SkinLoaderListener", "切换失败:" + errMsg)
                            }

                            override fun onProgress(progress: Int) {
                            }

                            override fun onStart() {
                            }
//                            fun onStart() {
//                                Log.i("SkinLoaderListener", "正在切换中")
//                                //dialog.show();
//                            }
//
//                            fun onSuccess() {
//                                Log.i("SkinLoaderListener", "切换成功")
//                            }
//
//                            fun onFailed(errMsg: String) {
//                                Log.i("SkinLoaderListener", "切换失败:" + errMsg)
//                            }
//
//                            fun onProgress(progress: Int) {
//                                Log.i("SkinLoaderListener", "皮肤文件下载中:" + progress)
//
//                            }
                        }

                )
            }
        }

    }

}
