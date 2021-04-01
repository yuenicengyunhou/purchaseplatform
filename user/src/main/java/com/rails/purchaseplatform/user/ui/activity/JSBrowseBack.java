package com.rails.purchaseplatform.user.ui.activity;

import android.webkit.JavascriptInterface;

import com.rails.purchaseplatform.common.utils.JSBack;

/**
 * webView 通过js调用原生代码
 * date:on 2017/7/5
 */
public interface JSBrowseBack extends JSBack {

    /**
     * 测试本地h5
     *
     * @param msg
     */
    @JavascriptInterface
    void postMessage(String msg);


    /**
     * 跳转结果页面
     *
     * @param type 0:审批单
     * @param msg
     */
    void onResult(int type, String msg);


}
