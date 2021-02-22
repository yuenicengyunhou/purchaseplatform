package com.rails.purchaseplatform.framwork.http.faction;


import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * author:wangchao
 * date:2018/11/2
 */
public class ErrorFunction<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
