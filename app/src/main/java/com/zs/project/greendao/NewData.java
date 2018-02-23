package com.zs.project.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zs
 * Date：2018年 02月 22日
 * Time：16:30
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
@Entity
public class NewData {

    private String src;
    private String weburl;
    private String time;
    private String pic;
    @Id
    private String title;
    private String category;
    private String url;

    @Generated(hash = 1985683279)
    public NewData(String src, String weburl, String time, String pic, String title,
            String category, String url) {
        this.src = src;
        this.weburl = weburl;
        this.time = time;
        this.pic = pic;
        this.title = title;
        this.category = category;
        this.url = url;
    }

    @Generated(hash = 1185707774)
    public NewData() {
    }

    @Override
    public String toString() {
        return "NewData{" +
                ", src='" + src + '\'' +
                ", weburl='" + weburl + '\'' +
                ", time='" + time + '\'' +
                ", pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getWeburl() {
        return this.weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
