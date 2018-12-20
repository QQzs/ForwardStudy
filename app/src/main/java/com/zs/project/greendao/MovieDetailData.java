package com.zs.project.greendao;

import com.zs.project.bean.movie.MovieCasts;
import com.zs.project.bean.movie.MovieImages;
import com.zs.project.bean.movie.MovieRating;
import com.zs.project.greendao.convert.MovieCastsConvert;
import com.zs.project.greendao.convert.MovieImagesConvert;
import com.zs.project.greendao.convert.MovieRatingConvert;
import com.zs.project.greendao.convert.StringConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：15:19
 * —————————————————————————————————————
 * About: 影片详情
 * —————————————————————————————————————
 */
@Entity
public class MovieDetailData{

    /**
     * {
     rating: {
     max: 10,
     average: 7.9,
     stars: "40",
     min: 0
     },
     genres: [
     "动作",
     "奇幻",
     "冒险"
     ],
     title: "海王",
     casts: [
     {
     alt: "https://movie.douban.com/celebrity/1022614/",
     avatars: {
     small: "http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32853.webp",
     large: "http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32853.webp",
     medium: "http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32853.webp"
     },
     name: "杰森·莫玛",
     id: "1022614"
     },
     {
     alt: "https://movie.douban.com/celebrity/1044702/",
     avatars: {
     small: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34697.webp",
     large: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34697.webp",
     medium: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34697.webp"
     },
     name: "艾梅柏·希尔德",
     id: "1044702"
     },
     {
     alt: "https://movie.douban.com/celebrity/1010539/",
     avatars: {
     small: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9206.webp",
     large: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9206.webp",
     medium: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9206.webp"
     },
     name: "威廉·达福",
     id: "1010539"
     }
     ],
     collect_count: 397576,
     original_title: "Aquaman",
     subtype: "movie",
     directors: [
     {
     alt: "https://movie.douban.com/celebrity/1032122/",
     avatars: {
     small: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509950363.8.webp",
     large: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509950363.8.webp",
     medium: "http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509950363.8.webp"
     },
     name: "温子仁",
     id: "1032122"
     }
     ],
     year: "2018",
     images: {
     small: "http://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.webp",
     large: "http://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.webp",
     medium: "http://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.webp"
     },
     alt: "https://movie.douban.com/subject/3878007/",
     id: "3878007"
     }
     */

    @Id(autoincrement = true)
    private Long id;
    private String title;
    @Convert(converter = MovieRatingConvert.class , columnType = String.class)
    private MovieRating rating;
    private String year;
    @Convert(converter = MovieImagesConvert.class , columnType = String.class)
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
    @Convert(converter = StringConvert.class , columnType = String.class)
    private List<String> countries;
    @Convert(converter = StringConvert.class , columnType = String.class)
    private List<String> genres;
    @Convert(converter = StringConvert.class , columnType = String.class)
    private List<String> aka;
    @Convert(converter = MovieCastsConvert.class , columnType = String.class)
    private List<MovieCasts> casts;
    @Convert(converter = MovieCastsConvert.class , columnType = String.class)
    private List<MovieCasts> directors;
    @Generated(hash = 1282668604)
    public MovieDetailData(Long id, String title, MovieRating rating, String year,
            MovieImages images, String alt, String mobile_url, String share_url,
            String schedule_url, String collect_count, String comments_count,
            String ratings_count, String original_title, String summary,
            List<String> countries, List<String> genres, List<String> aka,
            List<MovieCasts> casts, List<MovieCasts> directors) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.images = images;
        this.alt = alt;
        this.mobile_url = mobile_url;
        this.share_url = share_url;
        this.schedule_url = schedule_url;
        this.collect_count = collect_count;
        this.comments_count = comments_count;
        this.ratings_count = ratings_count;
        this.original_title = original_title;
        this.summary = summary;
        this.countries = countries;
        this.genres = genres;
        this.aka = aka;
        this.casts = casts;
        this.directors = directors;
    }
    @Generated(hash = 397581350)
    public MovieDetailData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public MovieRating getRating() {
        return this.rating;
    }
    public void setRating(MovieRating rating) {
        this.rating = rating;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public MovieImages getImages() {
        return this.images;
    }
    public void setImages(MovieImages images) {
        this.images = images;
    }
    public String getAlt() {
        return this.alt;
    }
    public void setAlt(String alt) {
        this.alt = alt;
    }
    public String getMobile_url() {
        return this.mobile_url;
    }
    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }
    public String getShare_url() {
        return this.share_url;
    }
    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
    public String getSchedule_url() {
        return this.schedule_url;
    }
    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }
    public String getCollect_count() {
        return this.collect_count;
    }
    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }
    public String getComments_count() {
        return this.comments_count;
    }
    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }
    public String getRatings_count() {
        return this.ratings_count;
    }
    public void setRatings_count(String ratings_count) {
        this.ratings_count = ratings_count;
    }
    public String getOriginal_title() {
        return this.original_title;
    }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
    public String getSummary() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public List<String> getCountries() {
        return this.countries;
    }
    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
    public List<String> getGenres() {
        return this.genres;
    }
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    public List<String> getAka() {
        return this.aka;
    }
    public void setAka(List<String> aka) {
        this.aka = aka;
    }
    public List<MovieCasts> getCasts() {
        return this.casts;
    }
    public void setCasts(List<MovieCasts> casts) {
        this.casts = casts;
    }
    public List<MovieCasts> getDirectors() {
        return this.directors;
    }
    public void setDirectors(List<MovieCasts> directors) {
        this.directors = directors;
    }

}
