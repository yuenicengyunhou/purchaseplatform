package com.rails.purchaseplatform.web.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.pop.AreaPop;
import com.rails.purchaseplatform.common.pop.QuickJumpPop;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.web.R;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 订单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */

@Route(path = ConRoute.WEB.WEB_ORDER_DETAIL)
public class OrderDetailActivity extends WebActivity<BaseWebBinding> implements JSEvaluteBack {

    private boolean isEvaseek = false;
    private boolean isEvacommit = false;
    private boolean isReceive = false;
    private UserInfoBean userInfoBean;
    private boolean neadResume = true;
    private QuickJumpPop pop;
    //    private QuickJumpPop quickJumpPop;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);

        isEvacommit = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_EVA_COMMIT, false);
        isEvaseek = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_EVA_SEEK, false);
        isReceive = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_RECEIVE, false);
        userInfoBean = PrefrenceUtil.getInstance(this).getBean(ConShare.USERINFO, UserInfoBean.class);
        url = ConRoute.WEB_URL.ORDER_DETAIL;
        String orderNo = extras.getString("orderNo");
        url = url + "?orderNo=" + orderNo + "&list=list" + "&isEvacommit=" + isEvacommit + "&isEvaseek=" + isEvaseek + "&isReceive=" + isReceive;
        if (userInfoBean != null) {
            url = url + "&userId=" + userInfoBean.getId();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
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
        ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
    }

    @JavascriptInterface
    public void goProductDetails(long platformId, long itemId) {
        Bundle bundle = new Bundle();
        bundle.putString("itemId", String.valueOf(itemId));
        ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
    }

    @JavascriptInterface
    @Override
    public void goDeliveredPage(String orderNo) {
        Bundle bundle = new Bundle();
        bundle.putString("orderNo", orderNo);
        ARouter.getInstance().build(ConRoute.ORDER.ORDER_DELIVER).with(bundle).navigation(this, 0);
    }

    @JavascriptInterface
    @Override
    public void callJump() {
        if (null == pop) {
            pop = new QuickJumpPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
        }
        pop.show(getSupportFragmentManager(), "quick");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            neadResume = false;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (null != pop) {
            pop.dismiss();
            pop = null;
        }
    }
}
