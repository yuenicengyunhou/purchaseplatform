package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.RecommendItemsService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class RecommendItemsModel {

    public void getRecommendItems(HttpRxObserver httpRxObserver) {
        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(RecommendItemsService.class, 1).getRecommendItems(params))
                .subscribe(httpRxObserver);
    }
}
