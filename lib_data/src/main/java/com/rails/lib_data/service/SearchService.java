package com.rails.lib_data.service;

import com.rails.lib_data.bean.SearchDataBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SearchService {


    /**
     * 获取商品列表
     *
     * @param params
     * @return
     */
    @GET("elasticsearch-service/mall/search/queryItemListByKeyword")
    Observable<HttpResult<SearchDataBean>> getItemListWithKeywordOnly(@QueryMap HashMap<String, Object> params);


    /**
     * 获取店铺列表
     *
     * @param params
     * @return
     */
    @GET("elasticsearch-service/mall/search/queryShopListByKeyword")
    Observable<HttpResult<ArrayList<Object>>> getShopListWithKeywordOnly(@QueryMap HashMap<String, Object> params);
}
