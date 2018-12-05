package com.zs.project.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.bean.wangyi.Article
import com.zs.project.listener.ItemClickListener
import com.zs.project.util.DateUtil
import com.zs.project.util.ImageLoaderUtil
import kotlinx.android.synthetic.main.item_article_text.view.*

/**
 *
Created by zs
Date：2018年 12月 05日
Time：10:12
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticleAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var mContext: Context? = null
    var mItemClickListener: ItemClickListener? = null
    var mData: MutableList<Article> = mutableListOf()

    constructor(mContext: Context? , itemClickListener: ItemClickListener?) : this() {
        this.mContext = mContext
        this.mItemClickListener = itemClickListener
    }

    fun updateData(data: MutableList<Article>){
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data: MutableList<Article>){
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TextHolder(View.inflate(parent?.context , R.layout.item_article_text,null))

    }

    override fun getItemViewType(position: Int): Int {

        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as TextHolder).bindData(position)

    }

    inner class TextHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position: Int) = with(itemView){
            var article = mData[position]
            tv_article_title?.text = article?.title
            ImageLoaderUtil.displayImage(article?.imgsrc , iv_article_img)
            tv_article_more?.text = DateUtil.friendlyTime(article?.ptime) + " " + article?.commentCount+ "跟帖"
            iv_article_img?.setOnClickListener {
                mItemClickListener?.onItemClick(position , article , iv_article_img)
            }
        }

    }

    inner class ImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(){

        }

    }

    inner class MoreImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(){

        }

    }


}