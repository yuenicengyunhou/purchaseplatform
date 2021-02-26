package com.rails.purchaseplatform.framwork.http.observer;


import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.UN_KNOWN_ERROR;

/**
 * 请求数据分发
 *
 * @param <T>
 */
public abstract class HttpRxObserver<T> implements Observer<T> {


    @Override
    public void onError(Throwable e) {
        if (e instanceof ErrorBean) {
            onError((ErrorBean) e);
        } else {
            onError(new ErrorBean(UN_KNOWN_ERROR, e + ""));
        }
    }


    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onStart(d);
    }


    protected void onStart(Disposable d) {

    }


    protected abstract void onError(ErrorBean e);


    protected abstract void onSuccess(T response);

}
