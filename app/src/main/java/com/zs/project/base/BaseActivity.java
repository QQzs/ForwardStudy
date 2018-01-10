package com.zs.project.base;

import android.app.Activity;
import android.view.View;

import com.zs.project.request.RequestApi;

import io.reactivex.Observable;

/**
 * Created by zs
 * Date：2017年 09月 25日
 * Time：11:24
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public abstract class BaseActivity extends BaseRxActivity implements View.OnClickListener{

    protected RequestApi mRequestApi = null;
    protected Activity mActivity;

    protected void initContentView(int layoutResID){
        setContentView(layoutResID);
        mRequestApi = RequestApi.getInstance();
        mActivity = this;
        /**
         * 初始化一些UI
         */
        init();

        /**
         * 初始化一些数据
         */
        initData();
    }

    public abstract void init();

    public abstract void initData();

    /**
     * requestData
     * @param request
     * @param type
     */
    protected abstract void requestData(Observable request, int type);
}
