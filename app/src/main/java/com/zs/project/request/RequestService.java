package com.zs.project.request;

import com.zs.project.bean.LoginBean;
import com.zs.project.bean.android.ArticleList;
import com.zs.project.bean.movie.MovieListData;
import com.zs.project.bean.news.NewListData;
import com.zs.project.bean.video.VideoData;
import com.zs.project.request.bean.BaseResponse;
import com.zs.project.request.bean.BaseResponseAndroid;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:34
 * —————————————————————————————————————
 * About: 请求接口
 * —————————————————————————————————————
 */

public interface RequestService {

    @GET("jisuapi/get")
    Observable<NewListData> newListDataRxjava(@QueryMap Map<String, Object> params);

    @GET("255-1")
    Observable<VideoData> getVideoListData(@QueryMap Map<String, String> params);

    @GET("v2/movie/{path}")
    Observable<MovieListData> getMovieListData(@Path("path") String path , @Query("start") int start , @Query("count") int count);

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponseAndroid<LoginBean>> loginAndroid(@Field("username") String username,
                                                            @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseResponseAndroid<LoginBean>> registerAndroid(@Field("username") String username,
                                                               @Field("password") String password,
                                                               @Field("repassword") String repassword);

    @GET("article/list/{page}/json")
    Observable<BaseResponseAndroid<ArticleList>> getArticleList(@Path("page") int page);

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
    Call<ResponseBody> getColllectList(@Path("page") int page);


    @GET("v2/movie/{path}")
    Call<ResponseBody> getTestData(@Path("path") String path , @Query("start") int start , @Query("count") int count);

    @GET("v2/movie/subject/{movieId}")
    Call<ResponseBody> getMovieDetailData(@Path("movieId") String movieId);

    @FormUrlEncoded
    @POST("android")
    Call<ResponseBody> getZBDatas(@Field("version") String version,
                                  @Field("optioncode") String optioncode,
                                  @Field("timestamp") String timestamp,
                                  @Field("nonce") String nonce,
                                  @Field("option") String option,
                                  @Field("signature") String signature);

    @GET("福利/10/1")
    Observable<BaseResponse> getRJData();


    @FormUrlEncoded
    @POST("android")
    Observable<BaseResponse> getRequestData(@Field("optioncode") String optioncode,
                                            @Field("option") String option);

}
