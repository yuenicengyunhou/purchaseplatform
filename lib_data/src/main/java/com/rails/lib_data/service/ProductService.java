package com.rails.lib_data.service;

import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
     */
    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts(@QueryMap HashMap<String, String> params);


    /**
     * 获取热销商品列表
     */
    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getHotProducts(@QueryMap HashMap<String, String> params);


    /**
     * 获取商品详情
     */
    @FormUrlEncoded
    @POST("app-item-service/app/v1/mall/search/queryNormalItemDetails")
    Observable<HttpResult<ProductDetailsBean>> getProductDetails(@FieldMap HashMap<String, Object> params);

    /**
     * 获取商品价格
     */
    @FormUrlEncoded
    @POST("app-item-service/app/v1/mall/search/querySkuPrice")
    Observable<HttpResult<ArrayList<ProductPriceBean>>> getProductPrice(@FieldMap HashMap<String, Object> params);

    /**
     * 获取店铺推荐
     */
    @FormUrlEncoded
    @POST("app-item-service/app/v1/mall/search/querySaleHotItem")
    Observable<HttpResult<ArrayList<HotSaleBean>>> getHotSale(@FieldMap HashMap<String, Object> params);

}
