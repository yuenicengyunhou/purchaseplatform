package com.rails.purchaseplatform.web.ui;

import android.content.Context;
import android.webkit.SslErrorHandler;

import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BaseActManager;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import androidx.viewbinding.ViewBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

import static com.rails.purchaseplatform.framwork.http.observer.BaseRetrofit.isDebug;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public abstract class BaseWebActivity<T extends ViewBinding> extends BaseErrorActivity<T> {


    private OkHttpClient.Builder setCertificates(OkHttpClient.Builder client, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            X509TrustManager trustManager = Platform.get().trustManager(sslSocketFactory);
            client.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }


    public void ssl(Context context, final SslErrorHandler handler, String url) {
        OkHttpClient.Builder builder;
        try {
            if (!isDebug)
                builder = setCertificates(new OkHttpClient.Builder(), context.getAssets().open("shop.cer"));
            else
                builder = setCertificates(new OkHttpClient.Builder(), context.getAssets().open("CARS-CA.cer"));
        } catch (IOException e) {
            builder = new OkHttpClient.Builder();
        }
        Request request = new Request.Builder().url(url)
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.proceed();
            }
        });
    }


    /**
     * 退出app
     */
    protected void goExit(){
        BaseActManager.getInstance().clear();
        System.exit(0);
    }


}
