package com.rails.purchaseplatform.framwork.bean;

/**
 * author :Created by sk.
 * date : 2016/9/27.
 * email: wangchao@lepu.cn
 */

public class ErrorBean extends Exception {


    private int code;
    private int errorcode;
    private String msg;

    public ErrorBean(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ErrorBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}
