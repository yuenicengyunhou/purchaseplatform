package com.rails.purchaseplatform.framwork.http.faction;

/**
 * 解析网络请求返回结果
 * author:wangchao
 * date:2018/11/2
 */
public class HttpResult<T> {
    private T data;
    private boolean success;
    private String msg;
    private String code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
