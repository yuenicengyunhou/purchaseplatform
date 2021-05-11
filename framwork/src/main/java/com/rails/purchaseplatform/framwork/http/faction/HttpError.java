package com.rails.purchaseplatform.framwork.http.faction;

/**
 * author:wangchao
 * date:2018/11/2
 */
public class HttpError extends RuntimeException {

    private String code;
    private String msg;

    private String timestamp;
    private String error;
    private String message;
    private String traceId;

    public HttpError(String code, String msg,String message) {
        this.code = code;
        this.msg = msg;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
