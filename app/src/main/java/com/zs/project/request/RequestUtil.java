package com.zs.project.request;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zs
 * Date：2018年 01月 17日
 * Time：11:26
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class RequestUtil {
    public static Observable getObservable(Observable request){
        return request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
