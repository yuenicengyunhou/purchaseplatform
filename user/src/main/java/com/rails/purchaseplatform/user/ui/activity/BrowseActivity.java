package com.rails.purchaseplatform.user.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivityUserBrowseBinding;

import java.util.HashMap;

/**
 * 浏览记录
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */
public class BrowseActivity extends BaseErrorActivity<ActivityUserBrowseBinding> implements JSBrowseBack {
    private String url;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        url = extras.getString("url");
    }

    @Override
    protected void initialize(Bundle bundle) {
//        url = "file:///android_asset/index.html";

        initWebView(binding.web, this);
    }

    @Override
    protected int getColor() {
        return R.color.bg_blue;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    /**
     * init webView
     *
     * @param webView
     */
    private void initWebView(WebView webView, JSBrowseBack jsMsgBack) {

        addHeader(webView, url);
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
        webView.addJavascriptInterface(jsMsgBack, "app");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showDialog("加载中...");
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                dismissDialog();
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("result", url);
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


    /**
     * 添加header
     *
     * @param url
     */
    public void addHeader(WebView webView, String url) {
        HashMap<String, String> params = new HashMap<>();
        String token = "thissitoken";
        params.put("token", token);
        if (params.size() <= 0)
            webView.loadUrl(url);
        else
            webView.loadUrl(url, params);
    }

    @JavascriptInterface
    @Override
    public void postMessage(String msg) {

    }

    @JavascriptInterface
    @Override
    public void onBack() {
        this.finish();
    }

    @JavascriptInterface
    public String getToken() {
        return "thisistoken";
    }


    @Override
    public void finish() {
        try {
            if (binding.web.canGoBack()) {
                binding.web.goBack();
            } else
                super.finish();
        } catch (Exception e) {
            super.finish();
        }

    }
}
