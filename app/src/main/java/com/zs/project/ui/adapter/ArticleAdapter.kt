package com.zs.project.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mcxtzhang.commonadapter.rv.CommonAdapter
import com.mcxtzhang.commonadapter.rv.OnItemClickListener
import com.mcxtzhang.commonadapter.rv.ViewHolder
import com.zs.project.R
import com.zs.project.greendao.ArticleData
import com.zs.project.listener.ItemClickListener
import com.zs.project.listener.ItemLongClickListener
import com.zs.project.ui.activity.WebViewActivity
import com.zs.project.util.DateUtil
import com.zs.project.util.ImageLoaderUtil
import com.zs.project.util.RecyclerViewUtil
import kotlinx.android.synthetic.main.item_article_image.view.*
import kotlinx.android.synthetic.main.item_article_image_more.view.*
import kotlinx.android.synthetic.main.item_article_text.view.*
import org.jetbrains.anko.startActivity

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
    var mItemLongClickListener : ItemLongClickListener? = null
    var mData: MutableList<ArticleData> = mutableListOf()

    constructor(mContext: Context? , itemClickListener: ItemClickListener? , itemLongClickListener : ItemLongClickListener?) : this() {
        this.mContext = mContext
        this.mItemClickListener = itemClickListener
        this.mItemLongClickListener = itemLongClickListener
    }

    fun updateData(data: MutableList<ArticleData>){
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data: MutableList<ArticleData>){
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    fun deleteData(position: Int){
        mData.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 ->{
                TextHolder(View.inflate(parent?.context , R.layout.item_article_text,null))
            }
            1 ->{
                MoreImageHolder(View.inflate(parent?.context , R.layout.item_article_image_more,null))
            }
            2 ->{
                ImageHolder(View.inflate(parent?.context , R.layout.item_article_image,null))
            }
            else ->{
                TextHolder(View.inflate(parent?.context , R.layout.item_article_text,null))
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        var type = mData[position].imgsrc3gtype
        return if (position == 0 && "2" == type){
            2
        } else if ("2" == type){
            1
        } else{
            0
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(getItemViewType(position)){
            0 ->{
                (holder as TextHolder).bindData(position)
            }
            1 ->{
                (holder as MoreImageHolder).bindData(position)
            }
            2 ->{
                (holder as ImageHolder).bindData(position)
            }
        }
        holder?.itemView?.setOnClickListener {
            var url = mData[position].url
            if (url?.contains("http") as Boolean){

            } else{
                url = mData[position].skipURL
            }
            mContext?.startActivity<WebViewActivity>("url" to url)

        }
        holder?.itemView?.setOnLongClickListener {
            mItemLongClickListener?.onItemLongClick(position , mData[position] , holder?.itemView)
            true
        }

    }

    inner class TextHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position: Int) = with(itemView){
            var article = mData[position]
            tv_article_title?.text = article?.title
            ImageLoaderUtil.displayImage(article?.imgsrc , iv_article_img)
            tv_article_more?.text = DateUtil.friendlyTime(article?.ptime) + " " + article?.commentCount+ "跟帖"
            iv_article_img?.setOnClickListener {
                mItemClickListener?.onItemClick(position , article.imgsrc , iv_article_img)
            }
        }

    }

    inner class MoreImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position: Int) = with(itemView){
            var article = mData[position]
            tv_article_title1?.text = article?.title
            tv_article_more1?.text = DateUtil.friendlyTime(article?.ptime) + " " + article?.commentCount+ "跟帖"
            var images = mutableListOf<String?>()
            images.add(article.imgsrc)
            article.imgextra?.run {
                for ( image in this){
                    images.add(image?.imgsrc)
                }
            }
            var adapter = object : CommonAdapter<String>(mContext , images , R.layout.item_image_layout){
                override fun convert(holder: ViewHolder?, url: String?) {
                    ImageLoaderUtil.loadImage(url , holder?.getView(R.id.iv_image_item))
                }
            }
            RecyclerViewUtil.initGridScroll(mContext , recycler_article_images , adapter , 3)
            adapter?.setOnItemClickListener(object : OnItemClickListener<String>{
                override fun onItemLongClick(p0: ViewGroup?, p1: View?, p2: String?, p3: Int): Boolean {
                    return false
                }

                override fun onItemClick(p0: ViewGroup?, p1: View?, p2: String?, p3: Int) {
                    if (p1 != null && p2 != null){
                        mItemClickListener?.onItemClick(p3 , p2!! , p1!!)
                    }
                }

            })
        }

    }

    inner class ImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position: Int) = with(itemView){
            var article = mData[position]
            tv_article_title2?.text = article?.title
            ImageLoaderUtil.displayImage(article?.imgsrc , iv_article_img2)

        }

    }

}