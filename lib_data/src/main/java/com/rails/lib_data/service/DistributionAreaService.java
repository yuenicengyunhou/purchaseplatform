package com.rails.lib_data.service;

import com.rails.lib_data.bean.address.AddressArea;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


/**
 * 获取各个级别配送区域Code
 */
public interface DistributionAreaService {


    /**
     * 获取各个级别配送区域Code
     *
     * @param map
     * @return
     */
    @GET("app-user-service/app/v1/mall/base/address/queryEnableAddsByParentCode")
    Observable<HttpResult<ArrayList<AddressArea>>> getDistributionArea(@QueryMap HashMap<String, Object> map);


}
