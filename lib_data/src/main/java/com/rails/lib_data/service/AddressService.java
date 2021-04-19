package com.rails.lib_data.service;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressListVO;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;



import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressService {

    /**
     * 根据采购商查询地址列表      账号类型，1，运营方，2.采购商，3供应商   platformId都写20
     */
    @GET("app-user-service/app/v1/buyer/address/queryAddressPageList")
    Observable<HttpResult<AddressListVO<AddressBean>>> getAddressList(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("accountType")
            int accountType, @Query("pageNum") long pageNum, @Query("pageSize") int pageSize);

    /**
     * 新增地址信息
     */
    @GET("user/buyer/address/insert")
    Observable<HttpResult<Boolean>> insertAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("requestStr") String addressJson);

    /**
     * 删除地址信息
     */
    @GET("user/buyer/address/delete")
    Observable<HttpResult<Boolean>> deleteAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("buyerAddressId") long buyerAddressId);

    /**
     * 重置默认地址--收货地址
     */
    @GET("user/buyer/address/updateDefaultAddress")
    Observable<HttpResult<String>> updateDefaultAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("id") long id);

    /**
     * 重置默认地址--发票地址
     */
    @GET("user/buyer/address/updateDefaultInvoiceAddress")
    Observable<HttpResult<String>> updateDefaultInvoiceAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("id") long id);


    /**
     * 更新地址
     */
    @GET("user/buyer/address/update")
    Observable<HttpResult<Boolean>> updateAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("requestStr") String addressJson, @Query("buyerAddressId") long buyerAddressId);
}