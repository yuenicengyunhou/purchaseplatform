package com.rails.lib_data.service;

import com.rails.lib_data.bean.ShopVO;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MallService {
    /**
     * 根据关键字查询店铺商品列表信息
     */
    @GET("elasticsearch-service/mall/search/queryShopItemListByKeyword")
    Observable<HttpResult<String>> queryShopItemListByKeyword();

    @GET("app-user-service/app/v1/mall/shop/queryShopInfoByShopInfoId")
    Observable<HttpResult<ShopVO>> queryShopInfoByShopInfoId(@Query("platformId") long platformId, @Query("shopInfoId") long shopInfoId);
}
