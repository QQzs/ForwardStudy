package com.zs.project.app;

import android.graphics.Color;
import android.os.Environment;

import com.zs.project.R;
import com.zs.project.util.FileUtils;

public class Constant {
    public static final String jcloudKey="6d119cf4202fec65d699ebb68d1d6e5f";
    public static final String showapi_sign="f255043723fe40839e61f6a40a6b0741";
    public static final String showapi_appid="44640";
    public static final String weatherKey="65f888e8c8ef49539f89a249a5e296ed";
    public static final String SAVED_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/meng";

    public static String PATH_DATA = FileUtils.createRootPath(MyApp.getAppContext()) + "/cache";
    public static String PATH_TXT = PATH_DATA + "/mengm/";
    public static String PATH_EPUB = PATH_DATA + "/epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

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

}
