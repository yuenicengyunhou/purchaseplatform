package com.rails.purchaseplatform.web.ui;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 浏览记录
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */

@Route(path = ConRoute.WEB.WEB_BROWSE)
public class BrowseActivity extends WebActivity<BaseWebBinding> implements JSBrowseBack {

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        url = ConRoute.WEB_URL.BROWSE;
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
    public void goProductDetails(long platformId, long itemId) {
        Bundle bundle = new Bundle();
        bundle.putString("itemId", String.valueOf(itemId));
        ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
    }


    @JavascriptInterface
    @Override
    public void onBack() {
        onBack(binding.web);
    }

    @JavascriptInterface
    @Override
    public void onLogin() {
//        ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
        refreshNewToken();
    }

    @JavascriptInterface
    @Override
    public void exit() {
        goExit();
    }
}
