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

    public RefreshEvent(String mFlag, boolean refresh) {
        this.mFlag = mFlag;
        this.refresh = refresh;
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
}
