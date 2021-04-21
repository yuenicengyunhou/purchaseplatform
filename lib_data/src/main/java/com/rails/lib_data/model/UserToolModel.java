package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.UserService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 获取用户信息
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public class UserToolModel {


    /**
     * 获取用户统计信息
     *
     * @param userId
     * @param userType
     * @param httpRxObserver
     */
    public void getUserStatictics(String userId, String userType, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");
        params.put("accountType", userType);
        params.put("accountId", userId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).getUserStatictics(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取用户统计信息
     *
     * @param userId
     * @param userType
     * @param httpRxObserver
     */
    public void getUserInfoStatictics(String userId, String userType, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");
        params.put("accountType", userType);
        params.put("accountId", userId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).getUserInfoStatictics(params))
                .subscribe(httpRxObserver);
    }
}
