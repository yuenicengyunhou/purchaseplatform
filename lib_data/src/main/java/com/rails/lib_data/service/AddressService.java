package com.rails.lib_data.service;

import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface AddressService {

    /**
     * 根据采购商查询地址列表      账号类型，1，运营方，2.采购商，3供应商   platformId都写20
     */

    @GET("app-user-service/app/v1/buyer/address/queryAddressPageList")
    Observable<HttpResult<ListBeen<AddressBean>>> getAddressList(@QueryMap HashMap<String, Object> map);


    @POST("user/buyer/address/queryBuyerAddressById")
    Observable<HttpResult<AddressBean>> getAddressInfo(@QueryMap HashMap<String, Object> map);


    /**
     * 新增地址信息
     */
    @GET("user/buyer/address/insert")
    Observable<HttpResult<Boolean>> insertAddress(@QueryMap HashMap<String, Object> map);

    /**
     * 删除地址信息
     */
    @GET("user/buyer/address/delete")
    Observable<HttpResult<Boolean>> deleteAddress(@QueryMap HashMap<String, Object> map);

    /**
     * 重置默认地址--收货地址
     */
    @GET("user/buyer/address/updateDefaultAddress")
    Observable<HttpResult<String>> updateDefaultAddress(@QueryMap HashMap<String, Object> map);

    /**
     * 重置默认地址--发票地址
     */
    @GET("user/buyer/address/updateDefaultInvoiceAddress")
    Observable<HttpResult<String>> updateDefaultInvoiceAddress(@QueryMap HashMap<String, Object> map);


    /**
     * 更新地址
     */
    @GET("user/buyer/address/update")
    Observable<HttpResult<Boolean>> updateAddress(@QueryMap HashMap<String, Object> map);


    /**
     * 获取省市区
     */
    @Deprecated
    @GET("app-user-service/app/v1/mall/base/address/queryEnableAddsByParentCode")
    Observable<HttpResult<ArrayList<AddressArea>>> getAddressCode(@QueryMap HashMap<String, Object> map);


    /**
     * 获取专属（收货地址/收发票地址）地址列表
     *
     * @param map
     * @return
     */
//    https://shop.rails.cn/proxy/app-user-service/app/v1/buyer/address/queryBuyerAddressListByBuyer?addressType=1&platformId=20
//    https://shop.rails.cn/proxy/user/buyer/address/queryBuyerAddressListByBuyer?addressType=1&platformId=20
    @GET("app-user-service/app/v1/buyer/address/queryBuyerAddressListByBuyer")
    Observable<HttpResult<ArrayList<AddressBean>>> getAddress(@QueryMap HashMap<String, Object> map);
}