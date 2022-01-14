package com.rails.purchaseplatform.web.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.pop.QuickJumpPop;
import com.rails.purchaseplatform.common.utils.StatusBarCompat;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.web.R;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

/**
 * 审批单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */

@Route(path = ConRoute.WEB.WEB_APPROVAL)
public class ApproveActivity extends WebActivity<BaseWebBinding> implements JSEvaluteBack {

    int type = 0;
    boolean isApprove = false;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        isApprove = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_APPROVE, false);
        type = extras.getInt("type", 0);
        if (type != 0) {
            url = url + "?kindApproval=" + type;
        } else {
            url = url + "?";
        }
        url += "&isApprove=" + isApprove;
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

    }


    @Override
    protected void onResume() {
        super.onResume();
        initWeb(binding.web, this);
    }
    protected void setDarkStatusBar(int color) {
        int statusBarColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarColor = ContextCompat.getColor(this, color);
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            statusBarColor = ContextCompat.getColor(this, color);
        }
        StatusBarCompat.compat(this, statusBarColor);
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
        finish();
    }


    @JavascriptInterface
    @Override
    public void onCopy(String code) {
        if (TextUtils.isEmpty(code))
            return;
        SystemUtil.copy(this, code);
        ToastUtil.show(this, code);
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
    public void callJump() {
        QuickJumpPop pop = new QuickJumpPop();
        pop.setGravity(Gravity.BOTTOM);
        pop.setType(BasePop.MATCH_WRAP);
        pop.show(getSupportFragmentManager(), "quick");
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

//    @Override
//    protected void isApprovalList(boolean isList) {
//        if (isList) {
//
//        } else {
//
//        }
//    }

    @Override
    protected void newLink(String url) {
        if (url.contains("/purOrderDetails")) {
            setDarkStatusBar(R.color.bg_blue);
        } else if (url.contains("/approvalList")) {
            setDarkStatusBar(R.color.white);
        }
    }
}
