package com.rails.purchaseplatform.market.ui.activity;

import android.webkit.JavascriptInterface;

/**
 * webView 通过js调用原生代码
 * date:on 2017/7/5
 */
public interface JSCallBack {

    /**
     * 测试本地h5
     *
     * @param msg
     */
    @JavascriptInterface
    void postMessage(String msg);


    /**
     * 返回
     */
    @JavascriptInterface
    void onBack();





}
