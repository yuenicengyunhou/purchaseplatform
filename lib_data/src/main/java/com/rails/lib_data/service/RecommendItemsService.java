package com.rails.lib_data.service;

import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RecommendItemsService {

    //首页
    @GET("passport/platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<RecommendItemsBean>>> getRecommendItems(@QueryMap HashMap<String, String> params);
}
