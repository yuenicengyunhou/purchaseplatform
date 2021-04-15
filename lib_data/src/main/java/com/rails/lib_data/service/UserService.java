package com.rails.lib_data.service;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressListVO;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    /**
     * 根据采购商查询地址列表      账号类型，1，运营方，2.采购商，3供应商   platformId都写20
     */
    @GET("user/buyer/address/queryAddressPageList")
    Observable<HttpResult<AddressListVO<AddressBean>>> getAddressList(@Query("platformId") long platformId, @Query("accountId") long accountId, @Query("accountType")
            int accountType, @Query("pageNum") long pageNum, @Query("pageSize") int pageSize);


    /**
     * 新增地址信息
     */
    @GET("user/buyer/address/insert")
    Observable<HttpResult<Boolean>> insertAddress(@Query("platformId") long platformId, @Query("accountId") long accountId,
                                                  @Query("organizationId") long organizationId, @Query("requestStr") String addressJson);

}