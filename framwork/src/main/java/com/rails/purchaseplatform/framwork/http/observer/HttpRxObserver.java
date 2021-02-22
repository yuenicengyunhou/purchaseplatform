package com.rails.purchaseplatform.framwork.http.observer;




import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract class HttpRxObserver<T> implements Observer<T>, HttpRequestListener {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ErrorBean) {
            onError((ErrorBean) e);
        } else {
            onError(new ErrorBean(10000, e + ""));
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

    @Override
    public void cancel() {

    }

    protected void onStart(Disposable d) {

    }


    protected abstract void onError(ErrorBean e);


    protected abstract void onSuccess(T response);

}
