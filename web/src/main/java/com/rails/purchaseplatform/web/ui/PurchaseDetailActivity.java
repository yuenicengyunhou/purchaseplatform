package com.rails.purchaseplatform.web.ui;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 采购单详情
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
@Route(path = ConRoute.WEB.WEB_PURCHASE_DETAIL)
public class PurchaseDetailActivity extends BaseWebActivity<BaseWebBinding> implements JSBrowseBack {


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

    @Override
    protected void initialize(Bundle bundle) {
        initWeb(binding.web, this);
    }


    @JavascriptInterface
    @Override
    public void postMessage(String msg) {

    }


    @JavascriptInterface
    @Override
    public void onResult(String json) {

    }



    @JavascriptInterface
    @Override
    public void onBack() {
        onBack(binding.web);
    }

    @JavascriptInterface
    @Override
    public void onLogin() {

    }
}
