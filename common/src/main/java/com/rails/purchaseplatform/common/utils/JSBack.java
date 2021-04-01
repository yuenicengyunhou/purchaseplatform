package com.rails.purchaseplatform.common.utils;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/1
 */
public interface JSBack {

    /**
     * 返回
     */
    void onBack();

    /**
     * 获取token
     *
     * @return
     */
    String getToken();


    /**
     * 登录过期，跳转app登录
     */
    void onLogin();
}
