package com.zs.project.request;

import com.zs.project.request.bean.BaseResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:34
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public interface RequestService {

    @GET("福利/10/1")
    Call<ResponseBody> getMzDatas();

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
