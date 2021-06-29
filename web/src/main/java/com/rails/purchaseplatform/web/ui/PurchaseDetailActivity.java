package com.rails.purchaseplatform.web.ui;

import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.pop.QuickJumpPop;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.web.R;
import com.rails.purchaseplatform.web.databinding.BaseWebBinding;

import org.greenrobot.eventbus.EventBus;

/**
 * 采购单详情
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
@Route(path = ConRoute.WEB.WEB_PURCHASE_DETAIL)
public class PurchaseDetailActivity extends WebActivity<BaseWebBinding> implements JSEvaluteBack {

    private boolean isEvaseek = false;
    private boolean isEvacommit = false;
    private boolean isReceive = false;
    private UserInfoBean userInfoBean;
    private QuickJumpPop pop;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);

        isEvacommit = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_EVA_COMMIT, false);
        isEvaseek = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_EVA_SEEK, false);
        isReceive = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_RECEIVE, false);
        userInfoBean = PrefrenceUtil.getInstance(this).getBean(ConShare.USERINFO, UserInfoBean.class);

        url = ConRoute.WEB_URL.PURCHASE_DETAIL;
        String orderNo = extras.getString("orderNo");
        url = url + "?orderNo=" + orderNo + "&handle=2&list=list" + "&isEvacommit=" + isEvacommit + "&isEvaseek=" + isEvaseek + "&isReceive=" + isReceive;
        if (userInfoBean != null) {
            url = url + "&userId=" + userInfoBean.getId();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initWeb(binding.web, this);
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
        return true;
    }

    @Override
    protected void initialize(Bundle bundle) {

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
    public void callJump() {
        if (null == pop) {
            pop = new QuickJumpPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.setQuickTabListener(this::toJump);
        }
        pop.show(getSupportFragmentManager(), "quick");
    }

    private void toJump(String type) {
        switch (type) {
            case "购物车":
                String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 2}";
                toActivity(ConRoute.RAILS.MAIN, json);
                break;
            case "首页":
                break;
            case "我的":
                break;
            case "搜索":
                break;
        }
    }

    private void toActivity(String path, String json) {
        ResultWebBean webBean = JsonUtil.parseJson(json, ResultWebBean.class);
        ARouter.getInstance()
                .build(webBean.getUrlright())
                .withParcelable("webBean", webBean)
                .navigation();


//        Bundle bundle = new Bundle();
//        bundle.putString("webBean", json);
//        ARouter.getInstance()
//                .build(path)
//                .navigation();
//        ResultWebBean bean = JsonUtil.parseJson(json, ResultWebBean.class);
//        EventBus.getDefault().post(new BusEvent<>(bean, ConRoute.EVENTCODE.MAIN_CODE));
//        activity.finish();

    }

    @Override
    public void goDeliveredPage(String orderNo) {

    }
}
