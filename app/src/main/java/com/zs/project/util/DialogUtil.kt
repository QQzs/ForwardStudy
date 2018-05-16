package com.zs.project.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.zs.project.R
import kotlinx.android.synthetic.main.dialog_notice_layout.view.*

/**
 * Created by zs
 * Date：2018年 03月 29日
 * Time：17:48
 * —————————————————————————————————————
 * About: Dialog工具类
 * —————————————————————————————————————
 */

class DialogUtil(private val mContext: Context?) {

    private var mDialogNoticeListener: DialogNoticeListener? = null
    private var mDialogBackListener: DialogBackListener? = null

    companion object {

        var mDialog: DialogUtil? = null

        fun getInstance(context: Context): DialogUtil {
            return if (null == mDialog) {
                DialogUtil(context)
            } else mDialog!!
        }
    }

    /**
     * 提示的dialog
     */
    fun showNoticeDialog(title : String , message : String): Dialog{

        val dialog = Dialog(mContext!! , R.style.public_dialog_style)
        val view = View.inflate(mContext,R.layout.dialog_notice_layout,null)
        dialog.setContentView(view)

        val tv_dialog_title = view.findViewById<TextView>(R.id.tv_dialog_title)
        val tv_dialog_message = view.findViewById<TextView>(R.id.tv_dialog_message)
        val tv_dialog_cancel = view.findViewById<TextView>(R.id.tv_dialog_cancel)
        val tv_dialog_comfirm = view.findViewById<TextView>(R.id.tv_dialog_comfirm)

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
            if (mContext != null && dialog != null && mDialogNoticeListener != null){
                mDialogNoticeListener?.onComfirmClick(dialog)
            }
        }
        view?.tv_dialog_cancel?.setOnClickListener{
            if (mContext != null && dialog != null && mDialogNoticeListener != null){
                mDialogNoticeListener?.onCancelClick(dialog)
            }
        }

        try {
            if (mContext != null && dialog != null){
                dialog.show()
            }
        } catch (e: Exception) {
        }
        return dialog

    }

    /**
     * 选择图片
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

    interface DialogNoticeListener {

        fun onComfirmClick(dialog: Dialog)

        fun onCancelClick(dialog: Dialog)
    }

    fun setDialogNoticeListener(listener: DialogNoticeListener) {
        this.mDialogNoticeListener = listener
    }


}
