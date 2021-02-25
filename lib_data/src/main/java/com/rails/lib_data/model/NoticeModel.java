package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.NoticeService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * 公告网络请求数据
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/25
 */
public class NoticeModel {

    public void getNotices(HttpRxObserver httpRxObserver) {

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(NoticeService.class).getNotices())
                .subscribe(httpRxObserver);

    }
}
