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
class MeAdapter(private var mData : MutableList<String>) : RecyclerView.Adapter<MeAdapter.MeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MeViewHolder {
        var view = View.inflate(parent?.context, R.layout.item_me_layout,null)
        return MeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun refreshData(data : ArrayList<String>){
        this.mData.clear()
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data : ArrayList<String>){
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MeViewHolder?, position: Int) {
        holder?.bindData(position)
    }


    inner class MeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindData(position: Int){
            itemView.setOnClickListener {
                ToastUtils.show("data" + position)
            }
        }
    }

}