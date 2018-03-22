package com.zs.project.request.bean;


/**
 * Created by zs
 * Date：2017年 09月 21日
 * Time：16:43
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class BaseRequest {

    private String version;
    private String optioncode;
    private String signature;
    private String timestamp;
    private String nonce;
    private String option;

    public BaseRequest(String optioncode, String option) {

        this.optioncode = optioncode;

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOptioncode() {
        return optioncode;
    }

    public void setOptioncode(String optioncode) {
        this.optioncode = optioncode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

}
