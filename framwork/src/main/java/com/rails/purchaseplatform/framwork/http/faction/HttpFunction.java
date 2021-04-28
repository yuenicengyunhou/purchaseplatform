package com.rails.purchaseplatform.framwork.http.faction;


import io.reactivex.functions.Function;

/**
 * 网络接口返回信息处理
 * author:wangchao
 * date:2018/11/2
 */
public class HttpFunction<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> tHttpResult) throws Exception {
        try{
            if (tHttpResult.isSuccess() || "0".equals(tHttpResult.getCode())) {

                T t = tHttpResult.getData();
                if (t == null)
                    return (T) tHttpResult.getMessage();
                else
                    return t;
            } else {
                throw new HttpError("0", tHttpResult.getMessage());
            }
        }catch (Exception e){
            throw new HttpError("0", tHttpResult.getMessage());
        }
    }
}
