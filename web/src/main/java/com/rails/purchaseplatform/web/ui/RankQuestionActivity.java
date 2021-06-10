package com.rails.purchaseplatform.web.ui;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/6/10
 */
@Route(path = ConRoute.WEB.WEB_RANK_QUESTION)
public class RankQuestionActivity extends WebActivity<BaseWebBinding> implements JSEvaluteBack {

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        url = ConRoute.WEB_URL.RANK_QUESTION;
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

    @Override
    public void onCopy(String code) {

    }

    @JavascriptInterface
    @Override
    public void goProductDetails(long platformId, long itemId) {
        Bundle bundle = new Bundle();
        bundle.putString("itemId", String.valueOf(itemId));
        ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
    }

    @Override
    public void goDeliveredPage(String orderNo) {

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
