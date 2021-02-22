package com.rails.purchaseplatform.framwork.http.observer;


import com.rails.purchaseplatform.framwork.http.faction.ErrorFunction;
import com.rails.purchaseplatform.framwork.http.faction.HttpFunction;
import com.rails.purchaseplatform.framwork.http.faction.HttpStringFunction;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 适用Retrofit网络请求Observable(被监听者)
 */
public class HttpRxObservable {

    /**
     * ·
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 无管理生命周期,容易导致内存溢出
     */
    public static Observable getObservable(Observable apiObservable) {
        Observable observable = apiObservable
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpFunction())
                .onErrorResumeNext(new ErrorFunction())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }


    public static Observable getObservables(Observable apiObservable) {
        Observable observable = apiObservable
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpStringFunction())
                .onErrorResumeNext(new ErrorFunction())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

}
