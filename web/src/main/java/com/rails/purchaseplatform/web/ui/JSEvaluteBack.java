package com.rails.purchaseplatform.web.ui;

import android.webkit.JavascriptInterface;

import com.rails.purchaseplatform.common.utils.JSBack;

/**
 * webView 通过js调用原生代码
 * date:on 2017/7/5
 */
public interface JSEvaluteBack extends JSBack {

    /**
     * 测试本地h5
     *
     * @param msg
     */
    @JavascriptInterface
    void postMessage(String msg);


    /**
     * {
     * "type":1,
     * "msg":"评价成功",
     * "btnleft":"查看采购单",
     * "btnright":"返回我的",
     * "urlleft":"/web/purchase/detail",
     * "urlright":"/rails/main"
     * }
     * <p>
     * <p>
     * 跳转结果页面
     *
     * @param json 返回json
     */
    void onResult(String json);


    /**
     * 粘贴
     * @param code
     */
    void onCopy(String code);


    /**
     * 跳转商品详情页面
     *
     * @param platformId
     * @param itemId
     */
    void goProductDetails(long platformId, long itemId);

    /**
     * 跳转到妥投文件页面
     */
    void goDeliveredPage(long platformId, String orderNo);


}
