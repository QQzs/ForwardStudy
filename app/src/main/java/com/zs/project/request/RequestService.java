package com.zs.project.request;

import com.zs.project.bean.LoginBean;
import com.zs.project.bean.android.ArticleBanner;
import com.zs.project.bean.android.ArticleList;
import com.zs.project.bean.movie.MovieListData;
import com.zs.project.request.bean.BaseResponseAndroid;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:34
 * —————————————————————————————————————
 * About: 请求接口
 * —————————————————————————————————————
 */

public interface RequestService {

    /**
     * 网易新闻
     * @param path
     * @param index
     * @param number
     * @return
     */
    @GET("/touch/reconstruct/article/list/{path}/{index}-{number}.html")
    Call<ResponseBody> getWangYI(@Path("path") String path , @Path("index") int index , @Path("number") int number);

    /**
     * 网易视频
     * @param index
     * @param number
     * @return
     */
    @GET("/touch/nc/api/video/recommend/{path}/{index}-{number}.do?callback=videoList")
    Call<ResponseBody> getWangYIVideo(@Path("path") String path , @Path("index") int index , @Path("number") int number);


    /**
     * 豆瓣电影列表
     * @param path
     * @param start
     * @param count
     * @return
     */
    @GET("v2/movie/{path}")
    Observable<MovieListData> getMovieListData(@Path("path") String path , @Query("start") int start , @Query("count") int count);

    /**
     * 玩android 登录
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponseAndroid<LoginBean>> loginAndroid(@Field("username") String username,
                                                            @Field("password") String password);

    /**
     * 玩android 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseResponseAndroid<LoginBean>> registerAndroid(@Field("username") String username,
                                                               @Field("password") String password,
                                                               @Field("repassword") String repassword);

    /**
     * 玩android 首页banner
     */
    @GET("/banner/json")
    Observable<BaseResponseAndroid<List<ArticleBanner>>> getArticleBanner();

    /**
     * 玩android 首页数据
     * @param page page
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponseAndroid<ArticleList>> getArticleList(@Path("page") int page);

    /**
     * 玩android 收藏列表
     * @param page page
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponseAndroid<ArticleList>> getCollectList(@Path("page") int page);

    /**
     * 玩android 收藏文章
     * @param id id
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponseAndroid<Object>> collectArticle(@Path("id") int id);

    /**
     * 玩android 取消收藏文章
     * @param id id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponseAndroid<Object>> unCollectArticle(@Path("id") int id);

    /**
     * 玩android 取消收藏文章
     * @param id id
     * @param originId originId
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<BaseResponseAndroid<Object>> unCollectArticleList(@Path("id") int id , @Field("originId") int originId);






    /**
     *
     * 测试请求
     *
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

    @GET("article/list/{page}/json")
    Call<ResponseBody> getArticleListTest(@Path("page") int page);

    @GET("lg/collect/list/{page}/json")
    Call<ResponseBody> getColllectListTest(@Path("page") int page);


    @GET("v2/movie/{path}")
    Call<ResponseBody> getTestData(@Path("path") String path , @Query("start") int start , @Query("count") int count);

    @POST("lg/collect/{id}/json")
    Call<ResponseBody> collectArticleTest(@Path("id") int id);

    @GET("v2/movie/subject/{movieId}")
    Call<ResponseBody> getMovieDetailData(@Path("movieId") String movieId);



    @POST("api/uaa/oauth/token")
    Call<ResponseBody> getToken(@Query("username") String username,
                                @Query("password") String password,
                                @Query("grant_type") String grant_type,
                                @Query("prod") String prod);

    @POST("api/uaa/leave")
    Call<ResponseBody> leaveApp();

    @GET("api/uas/open/personandusers/getInfo")
    Call<ResponseBody> getUser(@Query("loginName") String loginName);

    @GET("api/uas/open/resources/findByLoginNameAndProductCode")
    Call<ResponseBody> findProduct(@Query("hasSuperResource") String hasSuperResource,
                                   @Query("loginName") String loginName,
                                   @Query("productCode") String productCode);

}
