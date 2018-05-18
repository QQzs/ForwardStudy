package com.zs.project.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.zs.project.app.AppStatusManager;
import com.zs.project.app.Constant;
import com.zs.project.app.MyActivityManager;
import com.zs.project.request.RequestApi;
import com.zs.project.ui.activity.MainActivity;
import com.zs.project.util.DialogUtil;
import com.zs.project.util.SpUtil;

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
    protected String mUserId , mUserName;
    protected DialogUtil mDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (AppStatusManager.getInstance().getAppStatus()) {
            case AppStatusManager.STATUS_FORCE_KILLED:
                Log.d("My_Log_base", "AppStatusManager ==  STATUS_FORCE_KILLED");
                restartApp();
                break;
            case AppStatusManager.STATUS_NORMAL:
//              setUpViewAndData();
                break;
        }

    }

    protected void initContentView(int layoutResID){
        setContentView(layoutResID);
        mRequestApi = RequestApi.getInstance();
        mActivity = this;
        mUserId = SpUtil.getString(Constant.APP_USER_ID,"");
        mUserName = SpUtil.getString(Constant.APP_USER_NAME,"");
        mDialogUtil = DialogUtil.Companion.getInstance(this);
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

    protected void restartApp() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(AppStatusManager.KEY_HOME_ACTION,AppStatusManager.ACTION_RESTART_APP);
        startActivity(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        MyActivityManager.getActivityManager().finishAllActivity();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyActivityManager.getActivityManager().finishActivity(this);
    }
    
}
