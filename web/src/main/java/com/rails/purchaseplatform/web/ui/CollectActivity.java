package com.rails.purchaseplatform.web.ui;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.web.R;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 我的收藏
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */

@Route(path = ConRoute.WEB.WEB_COLLECT)
public class CollectActivity extends BaseWebActivity<BaseWebBinding> implements JSBrowseBack {


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
    public void onResult(int type, String msg) {

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
