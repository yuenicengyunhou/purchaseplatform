package com.rails.purchaseplatform.web.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.utils.JSBack;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.web.R;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.HashMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * 通用webview
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */

@Route(path = ConRoute.WEB.WEB_COMMON)
public
class CommonActivity extends ToolbarActivity<BaseWebBinding> implements JSBrowseBack {


    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    protected String url;
    protected String jsCls;
    protected String title;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        jsCls = extras.getString("module", "app");
        url = extras.getString("url");
        title = extras.getString("title", "网页");
    }

    public void onBack(WebView web) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (web.canGoBack()) {
                        web.goBack();
                    } else
                        finish();
                }
            });
        } catch (Exception e) {
            finish();
        }
    }


    @Override
    protected void initialize(Bundle bundle) {


        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black).setTitle(title);

        initWeb(barBinding.web, this);
    }

    @JavascriptInterface
    public String getToken() {
        return PrefrenceUtil.getInstance(this).getString(ConShare.TOKEN, "");
    }

    @JavascriptInterface
    @Override
    public void onLogin() {
        ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
    }

    @JavascriptInterface
    @Override
    public void exit() {
        try {
            BaseActManager.getInstance().clear();
            System.exit(0);
        } catch (Exception e) {
        }
    }


    /**
     * init webView
     *
     * @param webView
     */
    @SuppressLint("JavascriptInterface")
    protected void initWeb(WebView webView, JSBack jsBack) {

        addHeader(webView, url);
        Logger.d(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.d(newProgress);
                if (newProgress >= 70)
                    dismissDialog();

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });
        webView.addJavascriptInterface(jsBack, jsCls);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showDialog("加载中...");
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Logger.d("拦截服务端网络请求：" + request.getUrl().toString());
                return super.shouldInterceptRequest(view, request);

            }


            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                dismissDialog();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                ssl(CommonActivity.this, handler, view.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    view.reload();
                    return true;
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                dismissDialog();
            }

        });
    }


    public void ssl(Context context, final SslErrorHandler handler, String url) {
        OkHttpClient.Builder builder;
        try {
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
     * 添加header
     *
     * @param url
     */
    private void addHeader(WebView webView, String url) {
        HashMap<String, String> params = new HashMap<>();
        if (params.size() <= 0)
            webView.loadUrl(url);
        else
            webView.loadUrl(url, params);
    }


    @Override
    public void postMessage(String msg) {

    }

    @Override
    public void onResult(String json) {

    }

    @Override
    public void goProductDetails(long platformId, long itemId) {

    }


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

}
