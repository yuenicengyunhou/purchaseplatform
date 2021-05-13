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


    /**
     * 检查用户下单权限（信息核对页面）
     *
     * @param httpRxObserver
     */
    public void checkPermissions(String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).checkPermissions(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 检查权限
     *
     * @param httpRxObserver
     */
    public void queryResource(String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).queryResource(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 检查权限
     *
     * @param httpRxObserver
     */
    public void queryResourceButton(String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        params.put("owner","buyer");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).queryResourceButton(params))
                .subscribe(httpRxObserver);
    }
}
