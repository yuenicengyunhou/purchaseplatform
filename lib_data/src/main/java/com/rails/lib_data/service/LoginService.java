package com.rails.lib_data.service;

import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public interface LoginService {


    @FormUrlEncoded
    @POST("passport/appLogin")
    Observable<HttpResult<String>> onLogin(@FieldMap HashMap<String,String> params);

}
