package com.zs.project.request;

import android.text.TextUtils;
import android.util.Log;

import com.zs.project.app.MyApp;
import com.zs.project.request.cookie.CookieJarImpl;
import com.zs.project.request.cookie.SPCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:36
 * —————————————————————————————————————
 * About: 初始化请求Api
 * —————————————————————————————————————
 */
public class RequestApi {

    private final static int CONNECT_TIMEOUT = 20;
    private final static int READ_TIMEOUT = 10;

    public static final String BASE_NEW_URL = "https://way.jd.com/";
    public static final String BASE_SHOW_URL="http://route.showapi.com/";
    public static final String BASE_DOUBAN_URL = "https://api.douban.com/";
    public static final String BASE_WAN_ANDROID = "http://www.wanandroid.com/";

    public static final int REQUEST_DOUBAN = 1001;
    public static final int REQUEST_SHOW = 1002;
    public static final int REQUEST_NEWS = 1003;
    public static final int REQUEST_ANDROID = 1004;


    private static RequestApi mRetrofitApi;
    private HttpLoggingInterceptor mLoggingInterceptor;

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
            baseurl = BASE_DOUBAN_URL;
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
            case REQUEST_DOUBAN:
                baseUrl = BASE_DOUBAN_URL;
                break;
            case REQUEST_SHOW:
                baseUrl = BASE_SHOW_URL;
                break;
            case REQUEST_NEWS:
                baseUrl = BASE_NEW_URL;
                break;
            case REQUEST_ANDROID:
                baseUrl = BASE_WAN_ANDROID;
                break;
            default:
                baseUrl = BASE_DOUBAN_URL;
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
//        if (mOkHttpClient == null) {
//            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .retryOnConnectionFailure(true)//连接失败后是否重新连接
//                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//超时时间
//                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                    .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                    .cookieJar(new CookieJarImpl(new SPCookieStore(MyApp.getAppContext())));
//            mOkHttpClient = builder.build();
//        }
//        return mOkHttpClient;

        if (mLoggingInterceptor == null){
            mLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.e("RetrofitLog","retrofitBack = "+message);
                }
            });
            mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(mLoggingInterceptor)
                .cookieJar(new CookieJarImpl(new SPCookieStore(MyApp.getAppContext())));
        return builder.build();
    }

}
