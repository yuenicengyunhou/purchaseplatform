package com.rails.lib_data.service;

import com.rails.lib_data.bean.UserInfoBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public interface LoginService {


    /**
     * 手机号登录
     *
     * @param params
     * @return
     */
    @POST("passport/appLogin")
    Observable<HttpResult<String>> onLogin(@Body HashMap<String, String> params);


    /**
     * 获取验证码
     *
     * @return
     */
    @POST("passport/phoneCode/send/{phone}")
    Observable<HttpResult<String>> getCode(@Path("phone") String phone);

    /**
     * 刷新token
     */
    @POST("passport/refreshAppTicket")
    Observable<HttpResult<String>> refreshAppTicket(@Body HashMap<String, String> params);


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
    @POST("passport/passwd/findpd")
    Observable<HttpResult<String>> retrievePassword(@Body HashMap<String, Object> params);

    /**
     * 随机码登录 step1 获取标记
     *
     * @param code
     * @return
     */
    @POST("passport/randInit/{hex_md5_wz_code}")
    Observable<HttpResult<String>> randomInit(@Path("hex_md5_wz_code") String code);

    // TODO: 2021/6/17 返回值类型是什么？

    /**
     * 获取第一个坐标
     *
     * @param randInit
     * @return
     */
    @GET("passport/coordinate/v1/{rand_init}")
    Observable<HttpResult<String>> getCoordinate1(@Path("rand_init") String randInit);

    /**
     * 获取第二个坐标
     *
     * @param randInit
     * @return
     */
    @GET("passport/coordinate/v2/{rand_init}")
    Observable<HttpResult<String>> getCoordinate2(@Path("rand_init") String randInit);

    /**
     * 获取第三个坐标
     *
     * @param randInit
     * @return
     */
    @GET("passport/coordinate/v3/{rand_init}")
    Observable<HttpResult<String>> getCoordinate3(@Path("rand_init") String randInit);

    /**
     * 随机码登录
     *
     * @param params
     * @return
     */
    @POST("passport/appLogin")
    Observable<HttpResult<Object>> randomCodeLogin(@Body HashMap<String, String> params);
}
