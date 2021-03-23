package com.rails.purchaseplatform.market.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class RecommendPage4Js {
    Context mContext;

    public RecommendPage4Js(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {

    }

}
