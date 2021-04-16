package com.rails.lib_data.service;

import com.rails.lib_data.bean.CartBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
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
    @GET("app/v1/mall/cart/queryCart")
    Observable<HttpResult<CartBean>> getCarts(@QueryMap HashMap<String, Object> params);


    /**
     * 删除购物车
     *
     * @param params
     * @return
     */
    @POST("app/v1/mall/cart/deleteCart")
    Observable<HttpResult<CartBean>> delCart(@QueryMap HashMap<String, Object> params);


    /**
     * 加如购物车
     *
     * @param params
     * @return
     */
    @POST("app/v1/mall/cart/addCart")
    Observable<HttpResult<Object>> addCart(@QueryMap HashMap<String, Object> params);

}
