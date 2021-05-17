package com.rails.purchaseplatform.framwork.http.faction;


import android.text.TextUtils;

import io.reactivex.functions.Function;

/**
 * 网络接口返回信息处理
 * author:wangchao
 * date:2018/11/2
 */
public class HttpFunction<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> tHttpResult) {
        if (tHttpResult.isSuccess() || "0".equals(tHttpResult.getCode())) {

            T t = tHttpResult.getData();
            if (t == null) {
                if (TextUtils.isEmpty(tHttpResult.getMsg()) || "null".equals(tHttpResult.getMsg())) {
                    return (T) String.valueOf(tHttpResult.getMessage());
                } else {
                    return (T) String.valueOf(tHttpResult.getMsg());
                }

            } else
                return t;
        } else {
            throw new HttpError(tHttpResult.getCode(),
                    tHttpResult.getMsg() + "",
                    tHttpResult.getMessage() + "");
        }
    }
}
