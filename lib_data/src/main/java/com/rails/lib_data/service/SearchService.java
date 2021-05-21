package com.rails.lib_data.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.rails.lib_data.bean.forNetRequest.searchResult.SearchDataByItemBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.byShop.SearchDataByShopBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface SearchService {


    /**
     * 获取商品列表
     *
     * @param params
     * @return
     */
    @GET("elasticsearch-service/mall/search/queryItemListByKeyword")
    Observable<HttpResult<JsonObject>> getItemListWithKeywordOnly(@QueryMap HashMap<String, Object> params);


    /**
     * 获取店铺列表
     *
     * @param params
     * @return
     */
    @GET("elasticsearch-service/mall/search/queryShopListByKeyword")
    Observable<HttpResult<SearchDataByShopBean>> getShopListWithKeywordOnly(@QueryMap HashMap<String, Object> params);

    /**
     * 获取店铺列表
     *
     * @param params
     * @return
     */
    @GET("elasticsearch-service/mall/search/queryItemListByCid")
    Observable<HttpResult<SearchDataByItemBean>> getItemListWithCid(@QueryMap HashMap<String, Object> params);


    /**
     * 获取收货区域
     * Request URL: https://shop-dev1.rails.cn/proxy/user/mall/base/address/queryEnableAddsByParentCode?platformId=20&parentCode=0
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("app-user-service/app/v1/mall/base/address/queryEnableAddsByParentCode")
    Observable<HttpResult<JSONObject>> queryEnableAddsByParentCode(@FieldMap HashMap<String, Object> params);
}
