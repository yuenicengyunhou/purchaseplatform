package com.rails.lib_data.service;

import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.bean.SearchDataBean;
import com.rails.lib_data.request.SearchProductBody;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 商场--- 产品网络接口
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public interface ProductService {


    /**
     * 获取商城首页推荐商品列表
     *
     * @return
     */
    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts(@QueryMap HashMap<String, String> params);


    /**
     * 获取热销商品列表
     *
     * @return
     */
    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getHotProducts(@QueryMap HashMap<String, String> params);


    /**
     * 获取商品列表
     *
     * @return
     */
    @POST("elasticsearch-service/mall/search/queryItemListByKeyword")
    Observable<HttpResult<String>> getSearchResultWithKeywordOnly(@Body SearchProductBody body);


    /**
     * 获取商品列表
     */
    @GET("elasticsearch-service/mall/search/queryItemListByKeyword")
//    @GET("mall/search/queryItemListByKeyword")
    Observable<HttpResult<SearchDataBean>> getSearchResultWithKeywordOnly(@QueryMap HashMap<String, Object> params);

}
