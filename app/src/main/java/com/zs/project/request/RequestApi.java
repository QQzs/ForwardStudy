package com.zs.project.request;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:36
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class RequestApi {

    private final static int CONNECT_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;

    public static final String BASE_URL = "http://gank.io/api/data/";
    public static final String BASE_NEW_URL = "https://s.ibaodian.com/app/group/live/";

    public static final int REQUEST_URL1 = 1001;
    public static final int REQUEST_URL2 = 1002;
    public static final int REQUEST_URL3 = 1003;


    private static RequestApi mRetrofitApi;
    private OkHttpClient mOkHttpClient;

    public RequestApi(){

    }

    public static RequestApi getInstance() {
        if (mRetrofitApi == null) {
            mRetrofitApi = new RequestApi();
        }
        return mRetrofitApi;
    }

    /**
     * retrofit
     * @param baseurl
     * @return
     */
    public Retrofit getRetrofit(String baseurl){

        if (TextUtils.isEmpty(baseurl)) {
            baseurl = BASE_URL;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())   // 默认转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * 请求不同的基地址
     * @param type
     * @return
     */
    public RequestService getRequestService(int type) {
        String baseUrl;
        switch (type){
            case REQUEST_URL1:
                baseUrl = BASE_NEW_URL;
                break;
            case REQUEST_URL2:
                baseUrl = BASE_NEW_URL;
                break;
            case REQUEST_URL3:
                baseUrl = BASE_NEW_URL;
                break;
            default:
                baseUrl = BASE_URL;
        }
        return getRetrofit(baseUrl).create(RequestService.class);
    }

    /**
     * 请求默认的基地址
     * @return
     */
    public RequestService getBaseService() {
        return getRetrofit(null).create(RequestService.class);
    }

    /**
     * OKHttp
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)//连接失败后是否重新连接
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//超时时间
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

}
