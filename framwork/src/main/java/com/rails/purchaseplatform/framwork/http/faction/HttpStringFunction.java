package com.rails.purchaseplatform.framwork.http.faction;

import io.reactivex.functions.Function;

/**
 * 网络接口返回信息处理
 * author:wangchao
 * date:2018/11/2
 */
public class HttpStringFunction<String> implements Function<String, String> {

    @Override
    public String apply(String string) {
        return string;
    }
}
