package com.rails.lib_data.model;

import android.util.Log;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ShopService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class ShopModel {

    public void getShopInfo(long platformId, long shopInfoId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("shopInfoId", "202003030111");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ShopService.class).getShopInfo(map))
                .subscribe(httpRxObserver);
    }

    public void getShopItemList(long platformId, String shopInfoId, int page, int pageSize, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", "20");
        map.put("shopId", "202003030111");
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ShopService.class).getShopItemList(map))
                .subscribe(httpRxObserver);
    }
}

