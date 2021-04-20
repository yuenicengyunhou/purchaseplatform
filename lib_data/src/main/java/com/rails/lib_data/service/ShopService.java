package com.rails.lib_data.service;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ShopService {

    @POST("elasticsearch-service/mall/search/queryShopItemListByKeyword")
    Observable<Object> getShopInfo(@QueryMap HashMap<String, Object> map);
}
