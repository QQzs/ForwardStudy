package com.zs.project.event;

/**
 * Created by zs
 * Date：2018年 01月 23日
 * Time：11:36
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class RefreshEvent {

    private String mFlag;
    private boolean refresh;
    private int refresh_int;
    private String refresh_string;

    public RefreshEvent(String mFlag, boolean refresh) {
        this.mFlag = mFlag;
        this.refresh = refresh;
    }

    public RefreshEvent(String mFlag, int refresh_int) {
        this.mFlag = mFlag;
        this.refresh_int = refresh_int;
    }

    public RefreshEvent(String mFlag, String refresh_string) {
        this.mFlag = mFlag;
        this.refresh_string = refresh_string;
    }

    public String getmFlag() {
        return mFlag;
    }

    public void setmFlag(String mFlag) {
        this.mFlag = mFlag;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public int getRefresh_int() {
        return refresh_int;
    }

    public void setRefresh_int(int refresh_int) {
        this.refresh_int = refresh_int;
    }

    public String getRefresh_string() {
        return refresh_string;
    }

    public void setRefresh_string(String refresh_string) {
        this.refresh_string = refresh_string;
    }
}
