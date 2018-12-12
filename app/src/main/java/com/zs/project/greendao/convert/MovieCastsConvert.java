package com.zs.project.greendao.convert;

import com.alibaba.fastjson.JSON;
import com.zs.project.bean.movie.MovieCasts;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 12月 10日
 * Time：17:07
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MovieCastsConvert implements PropertyConverter<List<MovieCasts>, String>{

    @Override
    public List<MovieCasts> convertToEntityProperty(String databaseValue) {
        return JSON.parseArray(databaseValue , MovieCasts.class);
    }

    @Override
    public String convertToDatabaseValue(List<MovieCasts> entityProperty) {
        return JSON.toJSONString(entityProperty);
    }
}
