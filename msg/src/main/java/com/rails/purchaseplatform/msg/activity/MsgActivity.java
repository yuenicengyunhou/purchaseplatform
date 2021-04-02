package com.rails.purchaseplatform.msg.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.msg.databinding.ActivityMsgBinding;

import java.util.HashMap;

/**
 * 消息
 */
@Route(path = ConRoute.MSG.MSG_MAIN)
public class MsgActivity extends BaseErrorActivity<ActivityMsgBinding> {

    private String url;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        url = extras.getString("url");
    }

    @Override
    protected void initialize(Bundle bundle) {
//        url = "file:///android_asset/index.html";
//        url = ConRoute.WEB.MSG;

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
}