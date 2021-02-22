package com.rails.purchaseplatform.framwork.http.observer.down;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/4/15.
 */

public class DownInterceptor implements Interceptor {

    private DownloadRequestBody.DownloadProgressListener listener;

    public DownInterceptor(DownloadRequestBody.DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder newBuilder = chain
                .request()
                .newBuilder();

        Request request = newBuilder
                .addHeader("Accept-Encoding", "identity")
                .build();


        Response originalResponse = chain.proceed(request);

        return originalResponse.newBuilder()
                .body(new DownloadRequestBody(originalResponse.body(), listener))
                .build();
    }
}
