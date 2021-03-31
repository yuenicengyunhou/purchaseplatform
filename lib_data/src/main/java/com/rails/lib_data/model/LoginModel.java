package com.rails.lib_data.model;

import com.orhanobut.logger.Logger;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.LoginService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.MD5Util;

import java.util.HashMap;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class LoginModel {


    /**
     * 登录
     *
     * @param phone          手机号
     * @param paw            密码
     * @param code           验证码
     * @param httpRxObserver
     */
    public void onLogin(String phone, String paw, String code, HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("loginType", "1");
        params.put("mobilePhone", phone);
        params.put("userPassword",MD5Util.MD5(MD5Util.MD5(paw)));
        params.put("mobilePhoneCode", code);
        params.put("isWeakPwd", "0");
        params.put("returnUrl", "aaa");

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).onLogin(params))
                .subscribe(httpRxObserver);
    }
}
