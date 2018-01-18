package com.zs.project.bean.Movie;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：14:40
 * —————————————————————————————————————
 * About:列表影片详情
 * —————————————————————————————————————
 */

public class MovieListData {

    private int count;
    private int start;
    private int total;
    private MovieDetailData subjects;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public MovieDetailData getSubjects() {
        return subjects;
    }

    public void setSubjects(MovieDetailData subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MovieListData{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", subjects=" + subjects +
                ", title='" + title + '\'' +
                '}';
    }
}
