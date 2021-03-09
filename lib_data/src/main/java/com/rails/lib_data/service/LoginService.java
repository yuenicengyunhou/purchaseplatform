package com.rails.lib_data.service;

import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public interface LoginService {


    @GET("elasticSearch/queryBidNoticeListForMainPage")
    Observable<HttpResult<ArrayList<String>>> getTests();

}
