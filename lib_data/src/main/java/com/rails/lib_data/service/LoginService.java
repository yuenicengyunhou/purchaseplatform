package com.rails.lib_data.service;

import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public interface LoginService {


    /**
     * lognin
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("appLogin")
    Observable<HttpResult<String>> onLogin(@FieldMap HashMap<String, String> params);


    /**
     * 获取验证码
     *
     * @return
     */
    @POST("phoneCode/send/{phone}")
    Observable<HttpResult<String>> getCode(@Path("phone") String phone);

}
