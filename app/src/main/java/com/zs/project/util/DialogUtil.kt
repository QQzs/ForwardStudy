package com.zs.project.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.zs.project.R

/**
 * Created by zs
 * Date：2018年 03月 29日
 * Time：17:48
 * —————————————————————————————————————
 * About: Dialog工具类
 * —————————————————————————————————————
 */

class DialogUtil(private val mContext: Context?) {

    private var mDialogBackListener: DialogBackListener? = null

    companion object {

        var mDialog: DialogUtil? = null

        fun getInstance(context: Activity): DialogUtil {
            return if (null == mDialog) {
                DialogUtil(context)
            } else mDialog!!
        }
    }

    /**
     *
     */
    fun showAvatarDialog(): Dialog {

        val dialog = Dialog(mContext!!, R.style.public_dialog_style)
        val view = View.inflate(mContext, R.layout.dialog_select_image, null)
        dialog.setContentView(view)
        val tv_take_pic = view.findViewById<TextView>(R.id.tv_take_pic)
        val tv_album_pic = view.findViewById<TextView>(R.id.tv_album_pic)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)

        val window = dialog.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.decorView.setPadding(0, 0, 0, 0)
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }

        tv_take_pic.setOnClickListener {
            if (mContext != null && dialog != null && mDialogBackListener != null) {
                mDialogBackListener?.onComfirmClick(dialog)
            }
        }
        tv_album_pic.setOnClickListener {
            if (mContext != null && dialog != null && mDialogBackListener != null) {
                mDialogBackListener?.onBackClick(dialog)
            }
        }
        tv_cancel.setOnClickListener {
            if (mContext != null && dialog != null && mDialogBackListener != null) {
                mDialogBackListener?.onCancelClick(dialog)
            }
        }
        try {
            if (mContext != null && dialog != null) {
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


}
