package com.zs.project.request.bean;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：14:11
 * —————————————————————————————————————
 * About:返回数据的基类  根据需求设计
 * —————————————————————————————————————
 */

public class BaseResponse {

    private int status = -1;
    private String error;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
