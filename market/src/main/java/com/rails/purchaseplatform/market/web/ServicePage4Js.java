package com.rails.purchaseplatform.market.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class ServicePage4Js {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public ServicePage4Js(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {

    }

}
