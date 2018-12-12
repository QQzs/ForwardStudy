package com.zs.project.greendao.convert;

import com.alibaba.fastjson.JSON;
import com.zs.project.bean.movie.MovieImages;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by zs
 * Date：2018年 12月 10日
 * Time：17:06
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MovieImagesConvert implements PropertyConverter<MovieImages, String> {
    @Override
    public MovieImages convertToEntityProperty(String databaseValue) {
        return JSON.parseObject(databaseValue , MovieImages.class);
    }

    @Override
    public String convertToDatabaseValue(MovieImages entityProperty) {
        return JSON.toJSONString(entityProperty);
    }
}
