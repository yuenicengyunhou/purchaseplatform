package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.MallService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

public class ShopModel {

    /**
     * 获取店铺信息
     */
    public void getShopInfo(long platformId, long shopId, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MallService.class).queryShopInfoByShopInfoId(platformId, shopId)).subscribe(httpRxObserver);
    }
}
