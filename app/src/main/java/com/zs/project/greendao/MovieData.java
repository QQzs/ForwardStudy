package com.zs.project.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zs
 * Date：2018年 02月 22日
 * Time：16:53
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
@Entity
public class MovieData {

    @Id(autoincrement = true)
    private Long id;
    private String imageUrl;
    private String title;
    private String casts;
    private String genres;
    private String url;
    private float average;
    @Generated(hash = 1140047944)
    public MovieData(Long id, String imageUrl, String title, String casts,
            String genres, String url, float average) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.casts = casts;
        this.genres = genres;
        this.url = url;
        this.average = average;
    }
    @Generated(hash = 244871130)
    public MovieData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCasts() {
        return this.casts;
    }
    public void setCasts(String casts) {
        this.casts = casts;
    }
    public String getGenres() {
        return this.genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public float getAverage() {
        return this.average;
    }
    public void setAverage(float average) {
        this.average = average;
    }

}
