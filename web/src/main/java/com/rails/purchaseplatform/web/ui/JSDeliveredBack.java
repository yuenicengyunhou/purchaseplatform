package com.rails.purchaseplatform.web.ui;

import android.webkit.JavascriptInterface;

import com.rails.purchaseplatform.common.utils.JSBack;

public interface JSDeliveredBack extends JSBack {

    @JavascriptInterface
    void toDeliverPage(String params);
}
