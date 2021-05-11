package com.rails.lib_data.service;

import com.rails.lib_data.bean.UserInfoBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

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
    @POST("appLogin")
    Observable<HttpResult<String>> onLogin(@Body HashMap<String, String> params);


    /**
     * 获取验证码
     *
     * @return
     */
    @POST("phoneCode/send/{phone}")
    Observable<HttpResult<String>> getCode(@Path("phone") String phone);


    /**
     * 用户基本信息
     * https://shop.rails.cn/proxy/user/buyer/app-user-service/app/v1/buyer/user/login?platformId=30&serviceTicket=77ef7f0396413a3c1a5c41401bd34ca5
     *
     * @return
     */
    @GET("app-user-service/app/v1/buyer/user/login")
    Observable<HttpResult<UserInfoBean>> getUserInfo(@QueryMap HashMap<String, String> params);


    /**
     * 找回密码
     *
     * @param params
     * @return
     */
    @POST("passwd/findpd")
    Observable<HttpResult<String>> retrievePassword(@Body HashMap<String, Object> params);

}
