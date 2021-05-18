package com.rails.lib_data.service;

import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ShopService {

    @GET("app-user-service/app/v1/mall/shop/queryShopInfoByShopInfoId")
    Observable<HttpResult<ShopInfoBean>> getShopInfo(@QueryMap HashMap<String, Object> map);

    @GET("elasticsearch-service/mall/search/queryShopItemListByKeyword")
    Observable<HttpResult<ShopRecommendBean>> getShopItemList(@QueryMap HashMap<String, Object> map);
    @GET("elasticsearch-service/mall/search/queryItemListByCid")
    Observable<HttpResult<ShopRecommendBean>> getShopItemListByCid(@QueryMap HashMap<String, Object> map);

    //采购单号 从
}
