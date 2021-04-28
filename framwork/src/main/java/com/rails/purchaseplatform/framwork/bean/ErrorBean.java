package com.rails.purchaseplatform.framwork.bean;

/**
 * author :Created by sk.
 * date : 2016/9/27.
 * email: wangchao@lepu.cn
 */

public class ErrorBean extends Exception {


    private String code;
    private int errorcode;
    private String msg;
    private String data;

    public ErrorBean(Throwable throwable, String code) {
        super(throwable);
        this.code = code;
    }

    public ErrorBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
