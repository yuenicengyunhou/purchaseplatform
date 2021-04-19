package com.rails.lib_data.model;


import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ProductService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 请求商品详情
 */
public class ProductDetailsModel {

    /**
     * 请求商品详情
     *
     * @param platformId
     * @param itemId
     * @param companyId
     * @param httpRxObserver
     */

    public void getProductDetails(long platformId, long itemId, long companyId, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("itemId", itemId);
        params.put("companyId", companyId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductDetails(params))
                .subscribe(httpRxObserver);
    }
}
