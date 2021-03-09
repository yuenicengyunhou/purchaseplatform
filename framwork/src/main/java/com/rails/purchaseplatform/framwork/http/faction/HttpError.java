package com.rails.purchaseplatform.framwork.http.faction;

/**
 * author:wangchao
 * date:2018/11/2
 */
public class HttpError extends RuntimeException {

    private String code;
    private String msg;

    public HttpError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
