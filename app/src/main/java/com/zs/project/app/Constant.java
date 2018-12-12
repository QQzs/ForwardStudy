package com.zs.project.app;

import com.zs.project.R;
import com.zs.project.util.BtoAAtoB;
import com.zs.project.util.FileUtils;

public class Constant {

    public static final String APP_USER_ID="app_user_id";
    public static final String APP_USER_NAME="app_user_name";

    public static String PATH_DATA = FileUtils.createRootPath(MyApp.getAppContext()) + "/cache";
    public static String PATH_TXT = PATH_DATA + "/mengm/";

    public static final String[] iconNames = new String[]{
            "雪花" , "星星" , "爱心" , "金币" ,"红包"
    };

    public static final int[] iconId = new int[]{
            R.mipmap.ic_snow_img, R.mipmap.ic_star_img, R.mipmap.ic_heart_img
            , R.mipmap.ic_money_img , R.mipmap.ic_red_img
    };
    public static final String MOVIE_THEATERS = "in_theaters";
    public static final String MOVIE_COMING = "coming_soon";
    public static final String MOVIE_TOP = "top250";

    public static String TOKEN = "Basic " + BtoAAtoB.btoa("ecclient:ecclientsecret");

}
