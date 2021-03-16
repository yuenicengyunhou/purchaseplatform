package com.rails.purchaseplatform.market.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.utils.ToastUtil;
import com.rails.purchaseplatform.market.databinding.ActivityFindWebBinding;

import java.util.HashMap;

/**
 * 发现详情页面
 * author:wangchao
 * date:2018/11/13
 */
public class WebActivity extends ToolbarActivity<ActivityFindWebBinding> implements JSCallBack{


    private String url;





    @Override
    protected void initialize(Bundle bundle) {
        url = "file:///android_asset/index.html";
//        url ="http://172.28.20.109:3000/hotGoods";
        initWebView(barBinding.web, this);
    }

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



    /**
     * init webView
     *
     * @param webView
     */
    private void initWebView(WebView webView, JSCallBack jsCallBack) {

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


            @Override
            public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(WebActivity.this)
                                .setTitle("提示")
                                .setMessage(message)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                });
                result.confirm();
                return true;
            }
        });
        webView.addJavascriptInterface(jsCallBack, "app");
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
        if (params.size() <= 0)
            webView.loadUrl(url);
        else
            webView.loadUrl(url, params);
    }





    @Override
    public void onBack() {
        barBinding.web.stopLoading();
        super.onBack();
    }

    @JavascriptInterface
    @Override
    public void postMessage(String msg) {
        ToastUtil.showCenter(this,msg);
    }
}
