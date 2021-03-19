package com.rails.lib_data.service;

import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface HotSearchService {

    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<HotSearchBean>>> getHotSearch(@QueryMap HashMap<String, String> params);
}
