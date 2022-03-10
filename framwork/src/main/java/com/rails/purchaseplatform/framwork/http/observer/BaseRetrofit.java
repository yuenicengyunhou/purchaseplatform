package com.rails.purchaseplatform.framwork.http.observer;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.http.SSLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

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

    public static boolean isDebug = false;

    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒

    public abstract String getBaseUrl();

    public abstract String getBaseUrl(int type);


    /**
     * 获取host
     *
     * @param type 0：商城  1：平台
     * @return
     */
    public abstract String getHost(int type);

    public abstract Interceptor getInterceptor();


    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Logger.d(message));


    /**
     * @param type      0：商城 1：平台 2：数据分析排名
     * @param isVerfier 是否忽略证书认证
     * @return
     */
    public OkHttpClient getOkHttpClient(int type, boolean isVerfier) {

        //打印retrofit日志
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(SSLUtil.getHostVerifier(false, getHost(type)))
                .addInterceptor(getInterceptor())
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(SSLUtil.getSSLSocketFactory(isVerfier, isDebug), SSLUtil.getSSLManager(isVerfier, isDebug))
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();

        client.sslSocketFactory();
        return client;
    }


    public Retrofit getRetrofit(boolean isVerfier) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(0, isVerfier))

                .build();
        return mRetrofit;
    }


    public Retrofit getRetrofit(int type, boolean isVerfier) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(type))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(type, isVerfier))

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
        return getRetrofit(true).create(service);
    }


    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service, boolean isVerfier) {
        return getRetrofit(isVerfier).create(service);
    }


    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service, int type, boolean isVerfier) {
        return getRetrofit(type, isVerfier).create(service);
    }



    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service, int type) {
        return getRetrofit(type, true).create(service);
    }
}
