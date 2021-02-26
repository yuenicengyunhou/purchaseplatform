package com.rails.lib_data.model;

import com.rails.lib_data.bean.TestBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public interface LoginService {


    @GET("elasticSearch/queryBidNoticeListForMainPage")
    Observable<HttpResult<ArrayList<TestBean>>> getTests();

}
