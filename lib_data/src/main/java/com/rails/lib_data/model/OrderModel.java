package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.OrderService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class OrderModel {

    public void getRecOrder(HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getOrder(params))
                .subscribe(httpRxObserver);
    }

    public void getPurchasePageList(long platformId, String accountId, int queryType, int accountType, String squence, String content, int page, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("queryType", queryType);
        map.put("accountType", accountType);
        map.put(squence, content);
        map.put("pageSize", 10);
        map.put("pageNum", page);
//        map.put("buyerName", buyerName);
//        map.put("shopName", shopName);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).purchasePageList(map))
                .subscribe(httpRxObserver);
    }

    public void getBuyerNames(String like, String findType,String organizeId,HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nameLike", like);
        map.put("findType", findType);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getBuyerList(map))
                .subscribe(httpRxObserver);

    }

    public void getSupplierNames(String supplierName,String accountId,String accountType,String organizeName,String organizeId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("supplierName", supplierName);
        map.put("accountId", accountId);
        map.put("accountType", accountType);
        map.put("organizeName", organizeName);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getSupplierNames(map))
                .subscribe(httpRxObserver);

    }



}
