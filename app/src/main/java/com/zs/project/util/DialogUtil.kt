package com.zs.project.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.zs.project.R
import kotlinx.android.synthetic.main.dialog_notice_layout.view.*
import kotlinx.android.synthetic.main.dialog_select_image.view.*

/**
 * Created by zs
 * Date：2018年 03月 29日
 * Time：17:48
 * —————————————————————————————————————
 * About: Dialog工具类
 * —————————————————————————————————————
 */

class DialogUtil{

    private var mDialogNoticeListener: DialogNoticeListener? = null
    private var mDialogBackListener: DialogBackListener? = null

    companion object {

        var mDialog: DialogUtil? = null

        /**
         * 懒汉式实现单例
         */
//        fun getInstance(context: Context): DialogUtil {
//            return if (null == mDialog) {
//                DialogUtil(context)
//            } else mDialog!!
//        }

        /**
         * 静态内部类实现单例
         */
        fun getInstance(): DialogUtil{
            return DialogHoler.instance
        }

        class DialogHoler{
            companion object {
                val instance = DialogUtil()
            }
        }
    }

//    /**
//     * 两次判空实现单例
//     */
//    fun getInstance(): DialogUtil{
//        if (mDialog == null){
//            synchronized(DialogUtil::class.java){
//                if (mDialog == null){
//                    mDialog = DialogUtil()
//                }
//            }
//        }
//        return mDialog!!
//    }

    /**
     * 提示的dialog
     */
    fun showNoticeDialog(context : Context , title : String , message : String): Dialog{

        val dialog = Dialog(context!! , R.style.public_dialog_style)
        val view = View.inflate(context,R.layout.dialog_notice_layout,null)
        dialog.setContentView(view)

        val window = dialog.window
        if (window != null){
            window.setGravity(Gravity.CENTER)
            var lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }

        if (!StringUtils.isEmpty(title)){
            view?.tv_dialog_title?.text = title
        }
        view?.tv_dialog_message?.text = message

        view?.tv_dialog_comfirm?.setOnClickListener{
            if (context != null && dialog != null && mDialogNoticeListener != null){
                mDialogNoticeListener?.onComfirmClick(dialog)
            }
        }
        view?.tv_dialog_cancel?.setOnClickListener{
            if (context != null && dialog != null && mDialogNoticeListener != null){
                mDialogNoticeListener?.onCancelClick(dialog)
            }
        }

        try {
            if (context != null && dialog != null){
                dialog.show()
            }
        } catch (e: Exception) {
        }
        return dialog

    }

    /**
     * 选择图片
     */
    fun showAvatarDialog(context : Context?): Dialog? {
        if (context == null) return null
        val dialog = Dialog(context!!, R.style.public_dialog_style)
        val view = View.inflate(context, R.layout.dialog_select_image, null)
        dialog.setContentView(view)

        val window = dialog.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
//            window.decorView.setPadding(0, 0, 0, 0)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }

        view?.tv_take_pic?.setOnClickListener {
            if (context != null && dialog != null && mDialogBackListener != null) {
                mDialogBackListener?.onComfirmClick(dialog)
            }
        }
        view?.tv_album_pic?.setOnClickListener {
            if (context != null && dialog != null && mDialogBackListener != null) {
                mDialogBackListener?.onBackClick(dialog)
            }
        }
        view?.tv_cancel?.setOnClickListener {
            if (context != null && dialog != null && mDialogBackListener != null) {
                mDialogBackListener?.onCancelClick(dialog)
            }
        }
        try {
            if (context != null && dialog != null) {
                dialog.show()
            }
        } catch (e: Exception) {

        }
        return dialog
    }


    interface DialogBackListener {

        fun onComfirmClick(dialog: Dialog)

        fun onBackClick(dialog: Dialog)

        fun onCancelClick(dialog: Dialog)
    }

    fun setDialogBackListener(listener: DialogBackListener) {
        this.mDialogBackListener = listener
    }

    interface DialogNoticeListener {

        fun onComfirmClick(dialog: Dialog)

        fun onCancelClick(dialog: Dialog)
    }

    fun setDialogNoticeListener(listener: DialogNoticeListener) {
        this.mDialogNoticeListener = listener
    }


}
