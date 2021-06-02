package com.rails.purchaseplatform.web.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.purchaseplatform.common.utils.JSBack;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;


/**
 * webView基层类
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/1
 */
public abstract class WebActivity<T extends ViewBinding> extends BaseWebActivity<T> {

    protected String url;
    protected String jsCls;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        jsCls = extras.getString("module", "app");
        url = extras.getString("url", "");
        synCookies(url);
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

    @JavascriptInterface
    public String getToken() {
        return PrefrenceUtil.getInstance(this).getString(ConShare.TOKEN, "");
    }


    /**
     * init webView
     *
     * @param webView
     */
    @SuppressLint("JavascriptInterface")
    protected void initWeb(WebView webView, JSBack jsBack) {
        synCookies(url);
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
                synCookies(url);
                dismissDialog();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                ssl(WebActivity.this, handler, view.getUrl());
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
        Logger.d(url);
        addHeader(webView, url);
    }


    /**
     * 将cookie同步到WebView
     *
     * @param url WebView要加载的url
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void synCookies(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        /**
         * cookies是在HttpClient中获得的cookie
         */
        String token = PrefrenceUtil.getInstance(this).getString(ConShare.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        cookieManager.removeAllCookies(null);
        cookieManager.setCookie(url, "token" + "=" + token);
        /**
         *  判断系统当前版本，同步方式不一样
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(getApplicationContext()).sync();
        }
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
}
