package com.rails.lib_data.service;

import com.rails.lib_data.bean.AuthorButtonBean;
import com.rails.lib_data.bean.AuthorMenuBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public interface UserService {


    /**
     * 我的页面，数据统计数
     */
    @GET("app-order-service/app/v1/buyer/order/statisticsProcurementOrder")
    Observable<HttpResult<UserStatisticsBean>> getUserStatictics(@QueryMap HashMap<String, Object> params);


    /**
     * 我的页面，数据统计数
     */
    @GET("app-user-service/app/v1/buyer/user/personal")
    Observable<HttpResult<UserStatisticsBean>> getUserInfoStatictics(@QueryMap HashMap<String, Object> params);


    /**
     * 检测权限
     *
     * @return
     */
    @GET("app-user-service/app/v1/buyer/user/checkPermissions")
    Observable<HttpResult<UserStatisticsBean>> checkPermissions(@QueryMap HashMap<String, Object> params);


    /**
     * 个人中心，采购单/浏览记录/收藏列表时候可用
     *
     * @return
     */
    @GET("app-user-service/app/v1/buyer/roleresource/queryResourceByAccountId")
    Observable<HttpResult<ArrayList<AuthorMenuBean>>> queryResource(@QueryMap HashMap<String, Object> params);

    /**
     * 按钮
     *
     * @return
     */
    @GET("app-user-service/app/v1/buyer/roleresource/queryButtonByAccountId")
    Observable<HttpResult<ArrayList<AuthorButtonBean>>> queryResourceButton(@QueryMap HashMap<String, Object> params);


    @POST("portal/passWord/updatePassWordBySelf")
    Observable<HttpResult<Object>> updatePassword(@QueryMap HashMap<String, Object> params);
}
