package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.UserService;
import com.rails.lib_data.service.VersionService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * @author： sk_comic@163.com
 * @date: 2021/5/21
 */
public class VersionModel {


    /**
     * 获取用户统计信息
     *
     * @param httpRxObserver
     */
    public void getVersion(HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(VersionService.class, false).getVersion(params))
                .subscribe(httpRxObserver);
    }
}
