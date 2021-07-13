package com.rails.purchaseplatform.framwork.http.observer;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.http.observer.down.DownInterceptor;
import com.rails.purchaseplatform.framwork.http.observer.down.DownloadRequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
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

    public static boolean isDebug = true;

    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒

    public abstract String getBaseUrl();

    public abstract String getBaseUrl(int type);


    /**
     * 获取host
     * @param type 0：商城  1：平台
     * @return
     */
    public abstract String getHost(int type);

    public abstract Interceptor getInterceptor();


    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Logger.d(message));



    /**
     *
     * @param type 0：商城 1：平台 2：数据分析排名
     * @return
     */
    public OkHttpClient getOkHttpClient(int type) {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        //添加host认证
        final HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
//                return false;
                boolean verify = HttpsURLConnection.getDefaultHostnameVerifier().verify(getHost(type), session);
                return verify;
            }
        };

        //打印retrofit日志
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(verifier)
                .addInterceptor(getInterceptor())
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslSocketFactory, trustManager)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();

        client.sslSocketFactory();

        return client;
    }


    public Retrofit getRetrofit() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(0))

                .build();
        return mRetrofit;
    }


    public Retrofit getRetrofit(int type) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(type))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(type))

                .build();
        return mRetrofit;
    }

    /**
     * 下载网络请求
     *
     * param downloadProgressListener
     * return
     */
    public Retrofit downloadRetrofit(DownloadRequestBody.DownloadProgressListener downloadProgressListener) {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        DownInterceptor downloadInterceptor = new DownInterceptor(downloadProgressListener);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(downloadInterceptor)
                .sslSocketFactory(sslSocketFactory, trustManager)
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


    private InputStream trustedCertificatesInputStream() {
        InputStream inputStream = null;
        try {
            if (!isDebug)
                inputStream = BaseApp.getContext().getAssets().open("shop.cer");
            else
                inputStream = BaseApp.getContext().getAssets().open("CARS-CA.cer");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = "password".toCharArray();
        // Put the certificates a key store.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


    /**
     * 获取对应的Service
     *
     * param service Service 的 class
     * param <T>
     * return
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
