package com.rails.lib_data.model;

import android.text.TextUtils;
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



    /**
     * 排序：
     *
     * 销量向下
     * orderColumn=saleCount&orderType=desc
     * 销量向上
     * orderColumn=saleCount&orderType=asc
     *
     * 价格向上
     * orderColumn=sellPrice&orderType=asc
     * 价格向下
     * orderColumn=sellPrice&orderType=desc
     */
    public void getShopItemList(long platformId, long shopInfoId, int page, int pageSize,String orderColumn,String orderType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", "20");
        map.put("shopId", "202003030111");
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        if (!TextUtils.isEmpty(orderColumn)) {
            map.put("orderColumn", orderColumn);//排序字段
            map.put("orderType", orderType);//排序顺序
        }
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ShopService.class).getShopItemList(map))
                .subscribe(httpRxObserver);
    }
}

