package com.zs.project.bean;

import android.view.View;
import android.view.ViewGroup;

import com.zs.project.view.banner.BannerEntry;


/**
 * Created by zs
 * Date：2018年 01月 26日
 * Time：14:49
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class BannerBean implements BannerEntry {

    @Override
    public View onCreateView(ViewGroup parent) {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }

    @Override
    public CharSequence getSubTitle() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean same(BannerEntry newEntry) {
        return false;
    }
}
