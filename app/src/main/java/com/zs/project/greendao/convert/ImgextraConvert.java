package com.zs.project.greendao.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zs.project.bean.news.Imgextra;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 12月 07日
 * Time：10:56
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ImgextraConvert implements PropertyConverter<List<Imgextra>, String> {

    @Override
    public List<Imgextra> convertToEntityProperty(String databaseValue) {
//        return JSON.parseArray(databaseValue , Imgextra.class);
            return new Gson().fromJson(databaseValue , new TypeToken<List<Imgextra>>(){}.getType());
    }

    @Override
    public String convertToDatabaseValue(List<Imgextra> entityProperty) {
//        return JSON.toJSONString(entityProperty);
            return new Gson().toJson(entityProperty);
    }

}
