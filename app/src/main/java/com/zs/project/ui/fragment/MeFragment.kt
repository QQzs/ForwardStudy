package com.zs.project.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.donkingliang.imageselector.ClipImageActivity.SELECT_IMAGE
import com.donkingliang.imageselector.ClipImageActivity.TAKE_PHOTO
import com.donkingliang.imageselector.utils.ImageSelectorUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zs.project.R
import com.zs.project.app.Constant
import com.zs.project.base.BaseFragment
import com.zs.project.bean.ItemBean
import com.zs.project.listener.KotlinItemClickListener
import com.zs.project.request.RequestApi
import com.zs.project.ui.activity.LoginActivity
import com.zs.project.ui.activity.setting.CollectionActivity
import com.zs.project.ui.activity.setting.SettingActivity
import com.zs.project.ui.activity.setting.SkinChangeActivity
import com.zs.project.ui.adapter.MeItemAdapter
import com.zs.project.util.*
import kotlinx.android.synthetic.main.fragment_me_layout.*
import kotlinx.android.synthetic.main.zoom_header_layout.view.*
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
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
                mDialogUtil?.showAvatarDialog()
                mDialogUtil?.setDialogBackListener(object : DialogUtil.DialogBackListener{
                    override fun onComfirmClick(dialog: Dialog) {
                        dialog.dismiss()
                        mPermissions?.request(Manifest.permission.CAMERA)
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

//                rxPermissions
//                        .requestEach(
//                                Manifest.permission.CAMERA)
//                        .subscribe({
//
//                            if (it.granted){
//                                activity?.toast("ok")
//                            }else if (it.shouldShowRequestPermissionRationale){
//                                activity?.toast("拒绝")
//                            }else{
//                                activity?.toast("记住拒绝")
//                            }
//
//                        })


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
//                activity?.startActivity<AboutActivity>()
                activity?.startActivity<LoginActivity>()
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

}
