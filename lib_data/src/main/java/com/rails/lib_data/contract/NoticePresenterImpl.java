package com.rails.lib_data.contract;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.NoticeBean;
import com.rails.lib_data.bean.NoticeParentBean;
import com.rails.lib_data.model.NoticeModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;


import java.util.ArrayList;
import java.util.Set;

/**
 * 公告实现类
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/25
 */
public class NoticePresenterImpl extends BasePresenter<NoticeContract.NoticeView> implements NoticeContract.NoticePresenter {

    NoticeModel model;

    public NoticePresenterImpl(Activity mContext, NoticeContract.NoticeView noticeView) {
        super(mContext, noticeView);
        model = new NoticeModel();
    }

    @Override
    public void getNotice() {
        model.getNotices(new HttpRxObserver<JSONObject>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(JSONObject data) {
                baseView.dismissDialog();
                if (isCallBack()) {
                    ArrayList<NoticeParentBean> noticeParentBeans = new ArrayList<>();
                    Set<String> keys = data.keySet();
                    NoticeParentBean bean;
                    Gson gson = new GsonBuilder().create();
                    for (String key : keys) {
                        bean = new NoticeParentBean();
                        ArrayList<NoticeBean> list = gson.fromJson(String.valueOf(data.getJSONArray(key)),
                                new TypeToken<ArrayList<NoticeBean>>() {
                                }.getType());
                        bean.setKey(key);
                        bean.setSub(list);
                        noticeParentBeans.add(bean);
                    }
                    baseView.getNotice(noticeParentBeans);
                }

            }
        });
    }
}
