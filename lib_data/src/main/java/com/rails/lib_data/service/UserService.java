package com.rails.lib_data.service;

import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public interface UserService {


    /**
     * 我的页面，数据统计
     */
    @GET("/app/v1/buyer/order/statisticsProcurementOrder")
    Observable<HttpResult<UserStatisticsBean>> getShopListWithKeywordOnly(@QueryMap HashMap<String, Object> params);


}
