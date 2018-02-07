package com.zs.project.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.bean.ItemBean
import com.zs.project.listener.KotlinItemClickListener
import kotlinx.android.synthetic.main.item_me_layout.view.*

/**
 *
Created by zs
Date：2018年 02月 01日
Time：10:35
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class MeItemAdapter(private var mData : MutableList<ItemBean> , var mItemClickListener: KotlinItemClickListener) : RecyclerView.Adapter<MeItemAdapter.MeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MeViewHolder {
        var view = View.inflate(parent?.context, R.layout.item_me_layout, null)
        return MeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MeViewHolder?, position: Int) {
        holder?.bindData(position)
    }


    inner class MeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindData(position: Int){
            var bean = mData[position]
            itemView.iv_me_icon.setImageResource(bean.iconId)
            itemView.tv_me_title.text = bean.itemTitle

            itemView.setOnClickListener {
                if (mItemClickListener != null){
                    mItemClickListener.onItemClick(position,bean)
                }
            }
        }
    }

}