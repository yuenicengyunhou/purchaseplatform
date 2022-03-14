package com.rails.purchaseplatform.framwork.http;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 证书
 *
 * @author： sk_comic@163.com
 * @date: 2022/3/10
 */
public class SSLUtil {


    /**
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory(boolean isVerifier, boolean isDebug) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            if (isVerifier)
                sslContext.init(null, new TrustManager[]{trustManagerForCertificates(trustedCertificatesInputStream(isDebug))}, new SecureRandom());
            else
                sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }


    public static X509TrustManager getSSLManager(boolean isVerifier, boolean isDebug) {
        X509TrustManager manager = null;
        try {
            if (isVerifier)
                manager = trustManagerForCertificates(trustedCertificatesInputStream(isDebug));
            else
                manager = (X509TrustManager) getTrustManager()[0];
            return manager;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 读去本地证书
     *
     * @param isDebug
     * @return
     */
    private static InputStream trustedCertificatesInputStream(boolean isDebug) {
        InputStream inputStream = null;
        try {
            if (!isDebug)
                inputStream = BaseApp.getContext().getAssets().open("DigiCertGlobalRootCA.cer");
//                inputStream = BaseApp.getContext().getAssets().open("CARS-CA.cer");
            else
//                inputStream = BaseApp.getContext().getAssets().open("DigiCertGlobalRootCA.cer");
                inputStream = BaseApp.getContext().getAssets().open("CARS-CA.cer");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Logger.d("证书字符串："+ StreamUtil.readStream(inputStream));
        return inputStream;
    }


    private static X509TrustManager trustManagerForCertificates(InputStream in)
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


    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


    public static SSLSocketFactory ignoreSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //获取TrustManager
    public static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }


    /**
     * 获取验证域名
     *
     * @param hostName
     * @return
     */
    public static HostnameVerifier getHostVerifier(boolean isVerifier, String hostName) {
        HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if (isVerifier)
                    return HttpsURLConnection.getDefaultHostnameVerifier().verify(hostName, session);
                else
                    return true;
            }
        };
        return verifier;
    }


}
