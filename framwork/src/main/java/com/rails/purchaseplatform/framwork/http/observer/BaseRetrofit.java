package com.rails.purchaseplatform.framwork.http.observer;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.http.observer.down.DownInterceptor;
import com.rails.purchaseplatform.framwork.http.observer.down.DownloadRequestBody;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofitUtils工具类
 */

public abstract class BaseRetrofit {

    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒

    public abstract String getBaseUrl();

    public abstract String getBaseUrl(int type);

    public abstract Interceptor getInterceptor();


    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Logger.d(message);
        }
    });

    public OkHttpClient getOkHttpClient() {
        //打印retrofit日志
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();

        return client;
    }

    public Retrofit getRetrofit() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())

                .build();
        return mRetrofit;
    }


    public Retrofit getRetrofit(int type) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(type))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())

                .build();
        return mRetrofit;
    }

    /**
     * 下载网络请求
     *
     * @param downloadProgressListener
     * @return
     */
    public Retrofit downloadRetrofit(DownloadRequestBody.DownloadProgressListener downloadProgressListener) {

        DownInterceptor downloadInterceptor = new DownInterceptor(downloadProgressListener);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(downloadInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return mRetrofit;
    }


    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return getRetrofit().create(service);
    }


    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service, int type) {
        return getRetrofit(type).create(service);
    }
}
