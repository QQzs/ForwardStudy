package com.zs.project.bean.movie;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：15:19
 * —————————————————————————————————————
 * About: 影片详情
 * —————————————————————————————————————
 */

public class MovieDetailData implements Serializable{

    private String id;
    private String title;
    private MovieRating rating;
    private String year;
    private MovieImages images;
    private String alt;
    private String mobile_url;
    private String share_url;
    private String schedule_url;
    private String collect_count;
    private String comments_count;
    private String ratings_count;
    private String original_title;
    private String summary;
    private List<String> countries;
    private List<String> genres;
    private List<String> aka;
    private List<MovieCasts> casts;
    private List<MovieCasts> directors;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieRating getRating() {
        return rating;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MovieImages getImages() {
        return images;
    }

    public void setImages(MovieImages images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(String ratings_count) {
        this.ratings_count = ratings_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public List<MovieCasts> getCasts() {
        return casts;
    }

    public void setCasts(List<MovieCasts> casts) {
        this.casts = casts;
    }

    public List<MovieCasts> getDirectors() {
        return directors;
    }

    public void setDirectors(List<MovieCasts> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "MovieListData{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", rating=" + rating +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", mobile_url='" + mobile_url + '\'' +
                ", share_url='" + share_url + '\'' +
                ", schedule_url='" + schedule_url + '\'' +
                ", collect_count='" + collect_count + '\'' +
                ", comments_count='" + comments_count + '\'' +
                ", ratings_count='" + ratings_count + '\'' +
                ", original_title='" + original_title + '\'' +
                ", summary='" + summary + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                ", aka=" + aka +
                ", casts=" + casts +
                ", directors=" + directors +
                '}';
    }
}
