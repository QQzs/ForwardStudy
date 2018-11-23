package com.zs.project.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.greendao.NewData
import com.zs.project.listener.ItemClickListener
import com.zs.project.listener.ItemLongClickListener
import com.zs.project.util.DateUtil
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.LogUtil
import kotlinx.android.synthetic.main.new_list_item_layout.view.*

/**
 *
Created by zs
Date：2018年 01月 19日
Time：17:43
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class NewListAdapter(var mViewClickListener : ItemClickListener, var mItemLongClickListener : ItemLongClickListener): RecyclerView.Adapter<NewListAdapter.NewListHoler>(){

    private var mData :MutableList<NewData> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewListHoler {
        var itemView = LayoutInflater.from(parent?.context)?.inflate(R.layout.new_list_item_layout,null)
        return NewListHoler(itemView)
    }

    override fun onBindViewHolder(holder: NewListHoler, position: Int) {
        holder?.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun updateData(data :MutableList<NewData>){
        this.mData.clear()
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data : MutableList<NewData>){
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    fun getItemData(position: Int) : String{
        if (position <= mData.size){
            return DateUtil.getStandTime2(mData[position].time)
        }else{
            return "今天"
        }
    }


    inner class NewListHoler(itemView : View?) : RecyclerView.ViewHolder(itemView){

        fun bindData(position : Int){
            var bean = mData[position]
            itemView.tv_new_title?.text = bean?.title
            itemView.tv_new_time?.text = DateUtil.getStandTime1(bean?.time)

            LogUtil.logShow(bean?.time)

            ImageLoaderUtil.displayImage(bean?.pic,itemView?.iv_new_list_item)

            itemView.iv_new_list_item.setOnClickListener {
                mViewClickListener.onItemClick(position , bean , itemView.iv_new_list_item)
            }


            itemView.setOnClickListener {
                mViewClickListener.onItemClick(position , bean , itemView.rl_item_view)
            }

            itemView.setOnLongClickListener {
                mItemLongClickListener.onItemLongClick(position , bean , itemView.rl_item_view)
               true
            }
        }

    }


}
