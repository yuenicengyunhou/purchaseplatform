package com.rails.lib_data.service;

import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface HotSearchService {

    // TODO: 2021/04/19 修改请求地址
    @GET("/app-item-service/app/v1/mall/search/querySaleHotItem")
    Observable<HttpResult<ArrayList<HotSearchBean>>> getHotSearch(@QueryMap HashMap<String, String> params);
}
