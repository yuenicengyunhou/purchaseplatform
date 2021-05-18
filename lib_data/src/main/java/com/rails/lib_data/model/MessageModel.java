package com.rails.lib_data.model;

import com.rails.lib_data.bean.message.MessageRaw;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.MessageService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class MessageModel {


    /**
     * 获取消息列表
     */
    public void getMessageList(String platformId, String accountId,int pageNum,int pageSize, HttpRxObserver<MessageRaw> httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MessageService.class).getMessageList(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 查询站内未读信息数量
     */
    public void getNotReadMessageCount(String platformId, String accountId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MessageService.class).getNotReadMessageCount(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 修改站内信阅读状态
     */
    public void updateMessageStatus(String platformId, String accountId, String ids, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("ids", ids);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MessageService.class).updateMessageStatus(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 批量删除站内信
     */
    public void deleteMessages(String platformId, String accountId, String ids, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("ids", ids);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MessageService.class).deleteMessages(map))
                .subscribe(httpRxObserver);
    }
}
