package com.zs.project.greendao.convert;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 12月 10日
 * Time：17:16
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class StringConvert implements PropertyConverter<List<String> , String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        return JSON.parseArray(databaseValue , String.class);
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        return JSON.toJSONString(entityProperty);
    }
}
