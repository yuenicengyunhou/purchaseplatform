package com.rails.purchaseplatform.web.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.web.R;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 采购单详情
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
@Route(path = ConRoute.WEB.WEB_PURCHASE_DETAIL)
public class PurchaseDetailActivity extends WebActivity<BaseWebBinding> implements JSEvaluteBack {

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        url = ConRoute.WEB_URL.PURCHASE_DETAIL;
        String orderNo = extras.getString("orderNo");
        url = url + "?orderNo=" + orderNo + "&handle=2";

    }

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
    public void onResult(String json) {
        if (TextUtils.isEmpty(json))
            return;
        ResultWebBean bean = JsonUtil.parseJson(json, ResultWebBean.class);
        ARouter.getInstance()
                .build(ConRoute.MARKET.COMMIT_RESULT)
                .withParcelable("bean", bean)
                .navigation();
    }

    @Override
    public void onCopy(String code) {

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

    @JavascriptInterface
    public void goProductDetails(long platformId, long itemId) {
        Bundle bundle = new Bundle();
        bundle.putLong("platformId", platformId);
        bundle.putLong("itemId", itemId);
        ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
    }
}
