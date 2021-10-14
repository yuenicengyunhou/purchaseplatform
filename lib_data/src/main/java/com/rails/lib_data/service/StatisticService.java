package com.rails.lib_data.service;

/**
 * 流量统计
 *
 * @author： sk_comic@163.com
 * @date: 2021/8/26
 */

import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface StatisticService {


    /**
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("statistic-service/mall/flow/statistic/insertVisitRecord")
    Observable<HttpResult<String>> getVisitors(@FieldMap HashMap<String, Object> params);


}
