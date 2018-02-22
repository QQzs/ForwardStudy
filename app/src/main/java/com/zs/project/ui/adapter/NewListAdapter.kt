package com.zs.project.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.bean.News.NewListBean
import com.zs.project.listener.ItemClickListener
import com.zs.project.util.ImageLoaderUtil
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
class NewListAdapter(private var mData :MutableList<NewListBean> , var mViewClickListener : ItemClickListener): RecyclerView.Adapter<NewListAdapter.NewListHoler>(){



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NewListHoler {
        var itemView = LayoutInflater.from(parent?.context)?.inflate(R.layout.new_list_item_layout,null)
        return NewListHoler(itemView)
    }

    override fun onBindViewHolder(holder: NewListHoler?, position: Int) {
        holder?.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun updateData(data :MutableList<NewListBean>){
        this.mData.clear()
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data : MutableList<NewListBean>){
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    inner class NewListHoler(itemView : View?) : RecyclerView.ViewHolder(itemView){

        fun bindData(position : Int){
            var bean = mData.get(position)
            itemView.tv_new_title?.text = bean.title
            itemView.tv_new_time?.text = bean.time
            ImageLoaderUtil.displayImage(bean.pic,itemView.iv_new_list_item)

            itemView.iv_new_list_item.setOnClickListener {
                mViewClickListener.onItemClick(position , bean , itemView.iv_new_list_item)
            }


            itemView.setOnClickListener {
                mViewClickListener.onItemClick(position , bean , itemView.rl_item_view)
            }
        }

    }


}
