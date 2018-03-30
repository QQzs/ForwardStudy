package com.zs.project.ui.fragment

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.bean.ItemBean
import com.zs.project.event.SelectImageEvent
import com.zs.project.listener.KotlinItemClickListener
import com.zs.project.request.RequestApi
import com.zs.project.ui.activity.AboutActivity
import com.zs.project.ui.activity.setting.CollectionActivity
import com.zs.project.ui.activity.setting.SettingActivity
import com.zs.project.ui.activity.setting.SkinChangeActivity
import com.zs.project.ui.adapter.MeItemAdapter
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.PublicFieldUtil
import com.zs.project.util.RecyclerViewUtil
import com.zs.project.util.ScreenUtil
import kotlinx.android.synthetic.main.fragment_me_layout.*
import kotlinx.android.synthetic.main.zoom_header_layout.view.*
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    var mAdapter : MeItemAdapter?= null
    var mData : MutableList<ItemBean> = mutableListOf()
    var mZoomHeader : View? = null

    companion object {
        val SELECT_IMAGE : Int = 9000
    }

    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_me_layout)
        mFragment = this
        EventBus.getDefault().register(mFragment)
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
        mData?.add(ItemBean(R.mipmap.ic_me_skin,"换肤",3))
        mData?.add(ItemBean(R.mipmap.ic_me_about,"关于",4))

        mAdapter = MeItemAdapter(this,mData,this)
        RecyclerViewUtil.init(activity,recycler_view_me,mAdapter)
        recycler_view_me?.setPullRefreshEnabled(false)

        mZoomHeader = View.inflate(activity,R.layout.zoom_header_layout,null)
        recycler_view_me?.addZoomHeaderView(mZoomHeader, ScreenUtil.dp2px(200f))
        ImageLoaderUtil.loadAvatarImage(R.mipmap.ic_default_avatar,mZoomHeader?.iv_me_avator)
        ImageLoaderUtil.displayBlurImage(R.drawable.head_bg_img,mZoomHeader?.iv_zoom_img)

        mZoomHeader?.iv_me_avator?.setOnClickListener(this)

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
            R.id.iv_me_avator ->{

                val rxPermissions = RxPermissions(activity)

                //同时请求多个权限
                rxPermissions.request(Manifest.permission.RECEIVE_MMS,
                                Manifest.permission.READ_CALL_LOG)//多个权限用","隔开
                        .subscribe()
//                ImageSelectorUtils.openPhotoAndClip(activity,SELECT_IMAGE)
            }
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
                activity?.startActivity<SkinChangeActivity>()
            }
            4 ->{
                activity?.startActivity<AboutActivity>()
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setAvatar(event : SelectImageEvent){
        if ("avatar" == event.type){
            var avatar = event.getmImages()[0]
            ImageLoaderUtil.loadAvatarImage(avatar,mZoomHeader?.iv_me_avator)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(mFragment)
    }

}
