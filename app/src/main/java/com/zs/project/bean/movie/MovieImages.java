package com.zs.project.bean.movie;

import java.io.Serializable;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：15:15
 * —————————————————————————————————————
 * About: 电影海报 | 演员图片
 * —————————————————————————————————————
 */

public class MovieImages implements Serializable{

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "MovieImages{" +
                "small='" + small + '\'' +
                ", large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                '}';
    }
}
