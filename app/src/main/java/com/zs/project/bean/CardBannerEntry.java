package com.zs.project.bean;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kelin.banner.BannerEntry;
import com.zs.project.R;
import com.zs.project.util.ImageLoaderUtil;

/**
 * 创建人 kelin
 * 创建时间 2017/7/25  下午5:12
 * 版本 v 1.0.0
 */

public class CardBannerEntry implements BannerEntry<String> {
    private String title;
    private String subTitle;
    private String imgUrl;

    public CardBannerEntry(String title, String subTitle, String imgUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.imgUrl = imgUrl;
    }

    /**
     * 获取当前页面的布局视图。
     *
     * @param parent 当前的布局视图的父节点布局。
     * @return 返回当前页面所要显示的View。
     */
    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item_layout, parent, false);
        ImageView imageView = view.findViewById(R.id.iv_image);
        ImageLoaderUtil.displayImage(imgUrl,imageView);
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
        return newEntry instanceof CardBannerEntry && TextUtils.equals(title, newEntry.getTitle()) && TextUtils.equals(subTitle, newEntry.getSubTitle()) && TextUtils.equals(imgUrl, ((CardBannerEntry) newEntry).getImgUrl());
    }
}
