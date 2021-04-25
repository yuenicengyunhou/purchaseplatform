package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.HotSearchService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class HotSearchModel {

    public void getHotSearch(HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", 20L);
        params.put("listingStatus", 1);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(HotSearchService.class)
                .getHotSearch(params))
                .subscribe(httpRxObserver);
    }
}
