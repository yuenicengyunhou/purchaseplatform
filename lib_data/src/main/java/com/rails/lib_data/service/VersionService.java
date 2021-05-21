package com.rails.lib_data.service;

import com.rails.lib_data.bean.AuthorButtonBean;
import com.rails.lib_data.bean.VersionBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author： sk_comic@163.com
 * @date: 2021/5/21
 */
public interface VersionService {

    /**
     * 版本更新
     *
     * @return
     */
    @GET("app-user-service/app/v1/homePageExplain/queryVersions")
    Observable<HttpResult<ArrayList<VersionBean>>> getVersion(@QueryMap HashMap<String, Object> params);
}
