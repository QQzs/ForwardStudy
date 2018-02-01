package com.zs.project.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.util.ToastUtils

/**
 *
Created by zs
Date：2018年 02月 01日
Time：10:35
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class MeAdapter : RecyclerView.Adapter<MeAdapter.MeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MeViewHolder {
        var view = View.inflate(parent?.context, R.layout.item_me_layout,null)
        return MeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MeViewHolder?, position: Int) {
        holder?.bindData()
    }


    inner class MeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindData(){
            itemView.setOnClickListener {
                ToastUtils.show("ddddd")
            }
        }
    }

}