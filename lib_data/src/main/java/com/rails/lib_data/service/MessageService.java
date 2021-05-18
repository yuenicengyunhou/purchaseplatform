package com.rails.lib_data.service;

import com.rails.lib_data.bean.message.MessageRaw;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface MessageService {
    /**
     * 获取消息列表
     * 必传参数：platformId  accountId
     */
    @POST("platform/postman/websitemsg/queryWebSiteList")
    //{"code":"0","msg":"查询成功","data":{"allMessage":10,"notReadMessageCount":1,"alreadyReadMessageCount":9,"pageResult":{"pageNum":1,"pageSize":10,"totalCount":10,"totalPageCount":1,"result":[{"id":243071,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的采购单1210517140100019已下单，可以在订单列表中查看配送状态","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1621246202000,"created":1621238272000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":2},{"id":243057,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的采购单1210514111700033已下单，可以在订单列表中查看配送状态","serviceType":null,"wmHasRead":0,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":null,"created":1621217733000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":2},{"id":241320,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的采购单1210507094700018已下单，可以在订单列表中查看配送状态","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800607000,"created":1620721444000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":1},{"id":241260,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的采购单1210510094000003已下单，可以在订单列表中查看配送状态","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800613000,"created":1620628616000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":2},{"id":241258,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的采购单1210510141600010已下单，可以在订单列表中查看配送状态","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800613000,"created":1620628280000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":2},{"id":240058,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的【1210407095200017】订单已确认收货，如有异常请及时联系运营管理员。邀您点击http://shop.rails.cn/buyer-view/#/buyer-view/order/purchase/purchase-list查看详情","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800616000,"created":1618480801000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":2},{"id":239610,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"【2210324151400014】需求单将于2021年4月16日自动确认收货，如有货物问题请您及时处理。点击地址：https://shop.rails.cn/buyer-view/#/buyer-view/buyer/demand-info","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800617000,"created":1618459201000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":2},{"id":239509,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"【1210407095200017】采购单将于2021年4月15日自动确认收货，如有货物问题请您及时处理。点击地址：http://shop.rails.cn/buyer-view/#/buyer-view/order/purchase/purchase-list","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800618000,"created":1618372799000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":1},{"id":238402,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的订单【1210407095200017】已完成妥投，请及时签收，详细信息可登录采购系统后查看。","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800618000,"created":1617851086000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":1},{"id":238118,"platformId":20,"shopId":null,"directionalMessageId":null,"wmFromUserId":null,"wmFromUserName":null,"wmToUserId":1000088881,"wmToUserName":"cezdbj001","wmTitle":null,"wmContext":"您的采购单1210407095200017已下单，可以在订单列表中查看配送状态","serviceType":null,"wmHasRead":1,"lastWebsite":null,"wmSource":1,"wmBelongSystem":2,"modified":1620800618000,"created":1617785060000,"yn":1,"messageStatusView":"买家信息","creatTime":null,"allMessageOfDate":1}],"prevPage":1,"nextPage":2,"lastPage":true,"firstPage":true}}}
    Observable<HttpResult<MessageRaw>> getMessageList(@QueryMap HashMap<String, Object> map);


    /**
     * 查询站内未读信息数量
     * 必传参数：platformId  accountId
     */
    @GET("platform/postman/websitemsg/queryNotReadMessageCount")
    //{"code":"0","msg":"查询成功","data":{"notReadMessageCount":1}}
    Observable<HttpResult<MessageRaw>> getNotReadMessageCount(@QueryMap HashMap<String, Object> map);


    /**
     * 修改站内信阅读状态
     * 参数： platformId  accountId  ids
     */
    @POST("platform/postman/websitemsg/updateWebSiteStatus")
    Observable<HttpResult<Object>> updateMessageStatus(@QueryMap HashMap<String, Object> map);



    /**
     * 批量删除站内信
     * 参数： platformId  accountId  ids
     */
    @POST("platform/postman/websitemsg/deleteWebSite")
    Observable<HttpResult<Object>> deleteMessages(@QueryMap HashMap<String, Object> map);




}
