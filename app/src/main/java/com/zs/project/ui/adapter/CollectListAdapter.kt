package com.zs.project.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.project.R
import com.zs.project.greendao.MovieData
import com.zs.project.greendao.NewData
import com.zs.project.listener.ItemClickListener
import com.zs.project.util.ImageLoaderUtil
import kotlinx.android.synthetic.main.item_movie_layout.view.*
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
class CollectListAdapter(var mType : String , var mItemClickListener : ItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mNewData :MutableList<NewData> ?= null
    private var mMovieData :MutableList<MovieData> ?= null


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        var itemView : View ?= null
        when(mType){
           "news" ->{
               itemView = LayoutInflater.from(parent?.context)?.inflate(R.layout.new_list_item_layout,null)
               return NewListHoler(itemView)
           }
           "movies" ->{
               itemView = LayoutInflater.from(parent?.context)?.inflate(R.layout.item_movie_layout,null)
               return DouBanHolder(itemView)
           }
            else ->{
                return NewListHoler(itemView)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (mType) {
            "news" -> {
                (holder as NewListHoler).bindData(position)
            }
            "movies" -> {
                (holder as DouBanHolder).bindData(position)
            }

        }
    }

    override fun getItemCount(): Int {

        when(mType){
            "news" ->{
                return if(mNewData == null){
                    0
                }else{
                    mNewData!!.size
                }
            }
            "movies" ->{
                return if(mMovieData == null){
                    0
                }else{
                    mMovieData!!.size
                }
            }
            else ->{
                return 0
            }

        }

    }

    fun updateNewData(data :MutableList<NewData>){
        this.mNewData?.clear()
        this.mNewData = data
        notifyDataSetChanged()
    }
    fun appendNewData(data : MutableList<NewData>){
        this.mNewData?.addAll(data)
        notifyDataSetChanged()
    }

    fun updateMovieData(data :MutableList<MovieData>){
        this.mMovieData?.clear()
        this.mMovieData = data
        notifyDataSetChanged()
    }
    fun appendMovieData(data : MutableList<MovieData>){
        this.mMovieData?.addAll(data)
        notifyDataSetChanged()
    }

    inner class NewListHoler(itemView : View?) : RecyclerView.ViewHolder(itemView){

        fun bindData(position : Int){
            var bean = mNewData!![position]
            itemView.tv_new_title?.text = bean.title
            itemView.tv_new_time?.text = bean.time
            ImageLoaderUtil.displayImage(bean.pic,itemView.iv_new_list_item)

            itemView.iv_new_list_item.setOnClickListener {
                mItemClickListener.onItemClick(position , bean , itemView.iv_new_list_item)
            }


            itemView.setOnClickListener {
                mItemClickListener.onItemClick(position , bean , itemView.rl_item_view)
            }
        }

    }

    inner class DouBanHolder(itemView : View?) : RecyclerView.ViewHolder(itemView){

        fun bindData(position : Int){
            var detail = mMovieData!![position]
            ImageLoaderUtil.displayImage(detail.imageUrl,itemView?.iv_movie_img)
            itemView.tv_movie_title.text = detail.title
            itemView.tv_movie_casts.text = "主演：" + detail.casts
            itemView.tv_movie_genres.text = "类型：" + detail.genres

            itemView.star_movie_rating.starMark = detail.average / 2
            itemView.tv_movie_rating.text = detail.average.toString()

            itemView.setOnClickListener {
                mItemClickListener.onItemClick(position,detail,itemView.rl_movie_item)
            }

        }
    }


}
