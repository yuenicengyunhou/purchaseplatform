package com.rails.purchaseplatform.market.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.rails.purchaseplatform.common.utils.JSBack;

public class ProductInfoPage4Js implements JSBack {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public ProductInfoPage4Js(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {

    }

    @Override
    public void onBack() {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void onLogin() {

    }
}
