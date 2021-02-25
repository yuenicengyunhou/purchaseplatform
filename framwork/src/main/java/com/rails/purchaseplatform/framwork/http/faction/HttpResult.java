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
<<<<<<< HEAD
=======
    private String code;
>>>>>>> e41a6286c0069f3b4ddb89df3d61655c12d29065

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

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
<<<<<<< HEAD
=======
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
>>>>>>> e41a6286c0069f3b4ddb89df3d61655c12d29065
    }
}
