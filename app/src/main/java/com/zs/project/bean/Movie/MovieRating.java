package com.zs.project.bean.Movie;

import java.io.Serializable;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：15:20
 * —————————————————————————————————————
 * About: 电影评价
 * —————————————————————————————————————
 */
public class MovieRating implements Serializable {

    private int max;
    private float average;
    private int stars;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "MovieRating{" +
                "max=" + max +
                ", average=" + average +
                ", stars=" + stars +
                ", min=" + min +
                '}';
    }
}
