package com.rails.lib_data.service;

import com.alibaba.fastjson.JSONObject;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/25
 */
public interface NoticeService {


    /**
     * 获取公告数据
     *
     * @return
     */
    @GET("elasticSearch/queryBidNoticeListForMainPage")
    Observable<HttpResult<JSONObject>> getNotices();

}
