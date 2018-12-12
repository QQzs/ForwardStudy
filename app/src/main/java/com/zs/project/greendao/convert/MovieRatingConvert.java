package com.zs.project.greendao.convert;

import com.alibaba.fastjson.JSON;
import com.zs.project.bean.movie.MovieRating;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by zs
 * Date：2018年 12月 10日
 * Time：17:05
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MovieRatingConvert implements PropertyConverter<MovieRating, String>{

    @Override
    public MovieRating convertToEntityProperty(String databaseValue) {
        return JSON.parseObject(databaseValue , MovieRating.class);
    }

    @Override
    public String convertToDatabaseValue(MovieRating entityProperty) {
        return JSON.toJSONString(entityProperty);
    }
}
