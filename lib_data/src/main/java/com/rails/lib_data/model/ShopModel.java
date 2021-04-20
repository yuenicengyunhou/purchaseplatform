package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ShopService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class ShopModel {

    public void getShopInfo(long platformId, long shopInfoId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("shopInfoId", shopInfoId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ShopService.class).getShopInfo(map))
                .subscribe(httpRxObserver);
    }
}
