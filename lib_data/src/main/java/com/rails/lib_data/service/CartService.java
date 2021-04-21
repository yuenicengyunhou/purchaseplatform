package com.rails.lib_data.service;

import com.rails.lib_data.bean.CartBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 购物车service
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/15
 */
public interface CartService {


    /**
     * 获取购物车列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app-order-service/app/v1/mall/cart/queryCart")
    Observable<HttpResult<CartBean>> getCarts(@FieldMap HashMap<String, Object> params);


    /**
     * 删除购物车
     *
     * @param params
     * @return
     */
    @POST("app-order-service/app/v1/mall/cart/deleteCart")
    Observable<HttpResult<CartBean>> delCart(@QueryMap HashMap<String, Object> params);


    /**
     * 加如购物车
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("app-order-service/app/v1/mall/cart/addCart")
    Observable<HttpResult<String>> addCart(@FieldMap HashMap<String, Object> params);


    /**
     * 获取购物车总数量
     *
     * @param params
     * @return
     */
    @GET("app-order-service/app/v1/mall/cart/queryCartItemNum")
    Observable<HttpResult<String>> getCartNumber(@FieldMap HashMap<String, Object> params);


    /**
     * 更改单个商品选中状态
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("app-order-service/app/v1/mall/cart/selectedSku")
    Observable<HttpResult<Boolean>> modifySelect(@FieldMap HashMap<String, Object> params);


    /**
     * 更改全部商品选中状态
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("app-order-service/app/v1/mall/cart/selectedSkuAll")
    Observable<HttpResult<Boolean>> modifyAllSelect(@FieldMap HashMap<String, Object> params);

    /**
     * 校对购物车选中商品
     *
     * @param params
     * @return
     */
    @POST("app-order-service/app/v1/mall/cart/verifyCart")
    Observable<HttpResult<String>> verifyCart(@FieldMap HashMap<String, Object> params);


    /**
     * 加/减/编辑，商品数量
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("app-order-service/app/v1/mall/cart/changeSkuNum")
    Observable<HttpResult<Boolean>> modifyProductNum(@FieldMap HashMap<String, Object> params);

}
