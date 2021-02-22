package com.rails.purchaseplatform.framwork.http.observer.upload;



import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;

/**
 * 上传文件被观察者
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/1/22.
 */

public abstract class UploadObserver<T> extends DefaultObserver<T> {


    /**
     * 监听进度变化
     *
     * @param bytesWritten  写入的长度
     * @param contentLength 总长度
     */
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }

    //上传进度回调
    public abstract void onProgress(int progress);


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ErrorBean) {
            onError((ErrorBean) e);
        } else {
            onError(new ErrorBean(e, ExceptionEngine.UN_KNOWN_ERROR));
        }
    }

    @Override
    public void onComplete() {

    }


    protected abstract void onStart(Disposable d);


    protected abstract void onError(ErrorBean e);


    protected abstract void onSuccess(T response);
}
