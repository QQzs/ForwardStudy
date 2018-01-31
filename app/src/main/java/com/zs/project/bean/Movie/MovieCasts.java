package com.zs.project.bean.Movie;

import java.io.Serializable;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：15:17
 * —————————————————————————————————————
 * About: 主演 | 导演
 * —————————————————————————————————————
 */

public class MovieCasts implements Serializable{

    private String alt;
    private MovieImages avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public MovieImages getAvatars() {
        return avatars;
    }

    public void setAvatars(MovieImages avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MovieCasts{" +
                "alt='" + alt + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
