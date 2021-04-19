package com.rails.lib_data.service;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressListVO;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;


import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UserService {

    /**
     * 根据采购商查询地址列表      账号类型，1，运营方，2.采购商，3供应商   platformId都写20
     * HttpResult<AddressListVO<AddressBean>>
     */
    @GET("app-user-service/app/v1/buyer/address/queryAddressPageList")
    Observable<String> getAddressList(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("accountType")
            int accountType, @Query("pageNum") long pageNum, @Query("pageSize") int pageSize);

    @POST("app-user-service/app/v1/buyer/address/queryAddressPageList")
    Call<ResponseBody> getAddressList2(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("accountType")
            int accountType, @Query("pageNum") long pageNum, @Query("pageSize") int pageSize);

    /**
     * 新增地址信息
     */
    @GET("user/buyer/address/insert")
    Observable<HttpResult<Boolean>> insertAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("requestStr") String addressJson);

    /**
     * 删除地址信息
     */
    @GET("user/GET /buyer/address/delete")
    Observable<HttpResult<String>> deleteAddress(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("buyerAddressId") long buyerAddressId);

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
}