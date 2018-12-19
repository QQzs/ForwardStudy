package com.zs.project.bean;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zs.project.R;
import com.zs.project.util.ImageLoaderUtil;
import com.zs.project.view.banner.BannerEntry;

/**
 * 创建人 kelin
 * 创建时间 2017/7/25  下午5:12
 * 版本 v 1.0.0
 */

public class MovieBannerEntry implements BannerEntry<String> {
    private String title;
    private String movieId;
    private String imgUrl;
    private String alt;

    public MovieBannerEntry(String title, String id, String imgUrl,String alt) {
        this.title = title;
        this.movieId = id;
        this.imgUrl = imgUrl;
        this.alt = alt;
    }

    /**
     * 获取当前页面的布局视图。
     *
     * @param parent 当前的布局视图的父节点布局。
     * @return 返回当前页面所要显示的View。
     */
    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_movie_item_layout, parent, false);
        ImageView imageView = view.findViewById(R.id.iv_image);
        ImageLoaderUtil.loadImage(imgUrl,imageView);
        return view;
    }

    /**
     * 获取标题。
     *
     * @return 返回当前条目的标题。
     */
    @Override
    public CharSequence getTitle() {
        return title;
    }

    private String getImgUrl() {
        return imgUrl;
    }

    /**
     * 获取子标题。
     *
     * @return 返回当前条目的子标题。
     */
    @Nullable
    @Override
    public CharSequence getSubTitle() {
        return null;
    }

    public String getMovieId(){
        return movieId;
    }

    public String getAlt(){
        return alt;
    }
    /**
     * 获取当前页面的数据。改方法为辅助方法，是为了方便使用者调用而提供的，Api本身并没有任何调用。如果你不需要该方法可以空实现。
     *
     * @return 返回当前页面的数据。
     */
    @Override
    public String getValue() {
        return title;
    }

    @Override
    public boolean same(BannerEntry newEntry) {
        return newEntry instanceof MovieBannerEntry && TextUtils.equals(title, newEntry.getTitle()) && TextUtils.equals("", newEntry.getSubTitle()) && TextUtils.equals(imgUrl, ((MovieBannerEntry) newEntry).getImgUrl());
    }
}
