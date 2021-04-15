package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.UserService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

public class UserModel {
    /**
     * 获取维护地址列表
     */
    public void queryAddressList(long platformId, long accountId, int accountType, long pageNum, int pageSize, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).getAddressList(platformId,accountId,accountType,pageNum,pageSize))
                .subscribe(httpRxObserver);
    }


    /**
     * 添加地址
     */
    public void addAddress(long platformId, long accountId, long organizationId, String requestStr,HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class, 1).insertAddress(platformId,accountId,organizationId,requestStr))
                .subscribe(httpRxObserver);
    }
}
