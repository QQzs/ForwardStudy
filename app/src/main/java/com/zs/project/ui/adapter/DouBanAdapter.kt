package com.zs.project.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.bean.Movie.MovieDetailData
import com.zs.project.greendao.MovieData
import com.zs.project.listener.ItemClickListener
import com.zs.project.listener.ItemLongClickListener
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.StringUtils
import kotlinx.android.synthetic.main.item_movie_layout.view.*

/**
 *
Created by zs
Date：2018年 01月 30日
Time：13:31
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class DouBanAdapter(private var mData : MutableList<MovieDetailData>, var mItemClickListener: ItemClickListener, var mItemLongClickListener: ItemLongClickListener) : RecyclerView.Adapter<DouBanAdapter.DouBanHolder>() {

    override fun onBindViewHolder(holder: DouBanHolder?, position: Int) {
        holder?.bindData(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DouBanHolder {

        var view = LayoutInflater.from(parent?.context).inflate(R.layout.item_movie_layout,parent,false)
        return DouBanHolder(view)

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun refreshData(data : MutableList<MovieDetailData>){
        this.mData.clear()
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data : MutableList<MovieDetailData>){
        this.mData.addAll(data)
    }

    inner class DouBanHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position : Int){
            var detail = mData[position]
            ImageLoaderUtil.displayImage(detail.images.small,itemView?.iv_movie_img)
            itemView.tv_movie_title.text = detail.title
            itemView.tv_movie_casts.text = "主演：" + StringUtils.castsToString(detail.casts)
            itemView.tv_movie_genres.text = "类型：" + StringUtils.genresToString(detail.genres)

            itemView.star_movie_rating.starMark = detail.rating.average / 2
            itemView.tv_movie_rating.text = detail.rating.average.toString()

            itemView.setOnClickListener {
                mItemClickListener.onItemClick(position,detail,itemView.rl_movie_item)
            }

            itemView.setOnLongClickListener {
                var movie = MovieData(detail.id.toLong(),detail.images.small,itemView.tv_movie_title.text.toString(),itemView.tv_movie_casts.text.toString(),itemView.tv_movie_genres.text.toString(),detail.alt,detail.rating.average)
                mItemLongClickListener.onItemLongClick(position,movie,itemView.rl_movie_item)
                true

            }

        }
    }


}