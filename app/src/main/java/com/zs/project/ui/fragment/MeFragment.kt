package com.zs.project.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.donkingliang.imageselector.ClipImageActivity.SELECT_IMAGE
import com.donkingliang.imageselector.ClipImageActivity.TAKE_PHOTO
import com.donkingliang.imageselector.utils.ImageSelectorUtils
import com.jaeger.library.StatusBarUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zs.project.R
import com.zs.project.base.BaseFragment
import com.zs.project.bean.ItemBean
import com.zs.project.event.LoginEvent
import com.zs.project.listener.KotlinItemClickListener
import com.zs.project.ui.activity.AboutActivity
import com.zs.project.ui.activity.CollectionActivity
import com.zs.project.ui.activity.CollectionLocalActivity
import com.zs.project.ui.activity.LoginActivity
import com.zs.project.ui.activity.setting.SettingActivity
import com.zs.project.ui.activity.setting.SkinChangeActivity
import com.zs.project.ui.adapter.MeItemAdapter
import com.zs.project.util.*
import kotlinx.android.synthetic.main.fragment_me_layout.*
import kotlinx.android.synthetic.main.zoom_header_layout.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


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
    var mPermissions : RxPermissions? = null

    companion object {
//        val SELECT_IMAGE : Int = 9000
//        val TAKE_PHOTO : Int = 9001
    }

    /**
     * Bundle 后面不加 ？ 会报错误
     * Parameter specified as non-null is null
     */
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_me_layout)
        mFragment = this
        mPermissions = RxPermissions(activity!!)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setTranslucentForImageViewInFragment(activity , 0 , null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun initView() {
        super.initView()
//        mData?.add(ItemBean(R.mipmap.home_bar_article_nor,"我的文章",0))
        mData?.add(ItemBean(R.mipmap.home_bar_news_nor,"我的资讯",1))
        mData?.add(ItemBean(R.mipmap.home_bar_video_nor,"我的电影",2))
        mData?.add(ItemBean(R.mipmap.ic_me_setting,"设置",3))
        mData?.add(ItemBean(R.mipmap.ic_me_skin,"换肤",4))
        mData?.add(ItemBean(R.mipmap.ic_me_about,"关于",5))

        mAdapter = MeItemAdapter(this,mData,this)
        RecyclerViewUtil.init(activity,recycler_view_me,mAdapter)
        recycler_view_me?.setPullRefreshEnabled(false)

        mZoomHeader = View.inflate(activity,R.layout.zoom_header_layout,null)
        recycler_view_me?.addZoomHeaderView(mZoomHeader, ScreenUtil.dp2px(200f))
//        recycler_view_me?.addHeaderView(mZoomHeader)
        if (StringUtils.isNullOrEmpty(mUserId)){
            ImageLoaderUtil.loadAvatarImage(R.mipmap.default_img,mZoomHeader?.iv_me_avator)
            mZoomHeader?.tv_me_name?.text = "未登录"
        }else{
            ImageLoaderUtil.loadAvatarImage(R.mipmap.ic_default_avatar,mZoomHeader?.iv_me_avator)
            mZoomHeader?.tv_me_name?.text = mUserName
        }
        ImageLoaderUtil.displayLocalImage(R.drawable.header_bg_img,mZoomHeader?.iv_zoom_img)
//        ImageLoaderUtil.displayBlurImage(R.drawable.bg_monkey,mZoomHeader?.iv_zoom_img)

        mZoomHeader?.iv_me_avator?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.iv_me_avator ->{
                if (StringUtils.isNullOrEmpty(mUserId)){
                    activity?.startActivity<LoginActivity>()
                }else{
                    setUserAvatar()
                }
            }
        }

    }

    /**
     * 设置头像
     */
    private fun setUserAvatar(){
        mDialogUtil?.showAvatarDialog(context)
        mDialogUtil?.setDialogBackListener(object : DialogUtil.DialogBackListener{
            override fun onComfirmClick(dialog: Dialog) {
                dialog.dismiss()
                mPermissions?.request(Manifest.permission.CAMERA , Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ?.subscribe({
                            if (it){
                                ImageSelectorUtils.openCameraAndClip(activity,TAKE_PHOTO)
                            }else{
                                activity?.toast("相机权限已关闭")
                            }
                        })
            }

            override fun onBackClick(dialog: Dialog) {
                dialog.dismiss()
                //同时请求多个权限
                mPermissions?.request(Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
                        ?.subscribe({
                            if (it){
                                ImageSelectorUtils.openPhotoAndClip(activity,SELECT_IMAGE)
                            }else{
                                activity?.toast("读取存储卡权限已关闭")
                            }
                        })

            }

            override fun onCancelClick(dialog: Dialog) {
                dialog.dismiss()
            }

        })

    }


    override fun onItemClick(position: Int, data: Any) {

        var bean = data as ItemBean
        when(bean.type){
            0 ->{
                activity?.startActivity<CollectionActivity>()
            }
            1 ->{
                activity?.startActivity<CollectionLocalActivity>(PublicFieldUtil.FLAG_FIELD to "news")
            }
            2 ->{
                activity?.startActivity<CollectionLocalActivity>(PublicFieldUtil.FLAG_FIELD to "movies")
            }
            3 ->{
               activity?.startActivity<SettingActivity>()
            }
            4 ->{
                activity?.startActivity<SkinChangeActivity>()
            }
            5 ->{
                activity?.startActivity<AboutActivity>()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null){
            when(requestCode){
                TAKE_PHOTO ->{
                    var path = data?.getStringExtra(ImageSelectorUtils.SELECT_RESULT)
                    ImageLoaderUtil.loadAvatarImage(path,mZoomHeader?.iv_me_avator)
                }
                SELECT_IMAGE ->{
                    var path = data?.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT)[0]
                    ImageLoaderUtil.loadAvatarImage(path,mZoomHeader?.iv_me_avator)
                }
            }

        }else{
            activity?.toast("请重新选择")
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateUserData(user : LoginEvent){

        LogUtil.logShow("=== " + user.toString())
        mUserId = user.userId
        mUserName = user.userName

        if (StringUtils.isNullOrEmpty(mUserId)){
            ImageLoaderUtil.loadAvatarImage(R.mipmap.default_img,mZoomHeader?.iv_me_avator)
            mZoomHeader?.tv_me_name?.text = "未登录"
        }else{
            ImageLoaderUtil.loadAvatarImage(R.mipmap.ic_default_avatar,mZoomHeader?.iv_me_avator)
            mZoomHeader?.tv_me_name?.text = mUserName
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(activity)
    }


}
