package com.rails.purchaseplatform.web.ui;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 我的消息
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */

@Route(path = ConRoute.WEB.WEB_MSG)
public class MsgActivity extends WebActivity<BaseWebBinding> implements JSEvaluteBack {

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        url = ConRoute.WEB_URL.MSG;
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

    @Override
    public void goProductDetails(long platformId, long itemId) {

    }

    @Override
    public void goDeliveredPage(String orderNo) {

    }

    @JavascriptInterface
    @Override
    public void callJump() {

    }


    @JavascriptInterface
    @Override
    public void onBack() {
        onBack(binding.web);
    }

    @JavascriptInterface
    @Override
    public void onLogin() {
        ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
    }

    @Override
    public void exit() {
        goExit();
    }

}
