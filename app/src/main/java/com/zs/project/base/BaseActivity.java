package com.zs.project.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.zs.project.app.MyActivityManager;
import com.zs.project.request.RequestApi;
import com.zs.project.ui.activity.GuideActivity;

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

    protected Activity mActivity;
    protected RequestApi mRequestApi = null;
    protected Gson mGson = new Gson();

    protected void initContentView(int layoutResID){
        setContentView(layoutResID);
        mRequestApi = RequestApi.getInstance();
        mActivity = this;
        MyActivityManager.getActivityManager().addActivity(this);
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
    protected void requestData(Observable request, int type){

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MyActivityManager.finishAllActivity();
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyActivityManager.getActivityManager().finishActivity(this);
    }
}
