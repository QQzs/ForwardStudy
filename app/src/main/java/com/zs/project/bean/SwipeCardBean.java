package com.zs.project.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/18.
 */

public class SwipeCardBean {
    private int postition;
    private String name;

    public SwipeCardBean(int postition,String name) {
        this.postition = postition;
        this.name = name;
    }

    public int getPostition() {
        return postition;
    }

    public SwipeCardBean setPostition(int postition) {
        this.postition = postition;
        return this;
    }

    public String getName() {
        return name;
    }

    public SwipeCardBean setName(String name) {
        this.name = name;
        return this;
    }

    public static List<SwipeCardBean> initDatas() {
        List<SwipeCardBean> datas = new ArrayList<>();
        int i = 0;
        datas.add(new SwipeCardBean(i++,"雪花"));
        datas.add(new SwipeCardBean(i++,"星星"));
        datas.add(new SwipeCardBean(i++,"爱心"));
        datas.add(new SwipeCardBean(i++,"雪花"));
        datas.add(new SwipeCardBean(i++,"星星"));
        datas.add(new SwipeCardBean(i++,"爱心"));
        return datas;
    }
}
