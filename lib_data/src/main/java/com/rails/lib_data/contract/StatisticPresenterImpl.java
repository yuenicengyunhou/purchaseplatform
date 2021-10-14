package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.model.StatisticModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

/**
 * 流量统计
 *
 * @author： sk_comic@163.com
 * @date: 2021/8/13
 */
public class StatisticPresenterImpl extends BasePresenter<StatisticContract.StatisticView> implements StatisticContract.StatisticPresenter {

    StatisticModel model;


    public StatisticPresenterImpl(Activity mContext, StatisticContract.StatisticView statisticView) {
        super(mContext, statisticView);
        model = new StatisticModel();
    }


    /**
     * platformId    平台id 默认20
     * visitorType   参观者类型      0：匿名用户   1：登陆用户
     * cookieFinger  cookie指纹，就是当前时间戳
     * sourceType    来源信息 0：pc    1：app
     *
     * @param materialType 物质类型  0：通用   1：专用（商品详情用）
     */
    @Override
    public void getVisitors(String materialType, String itemShopId, String skuId) {
        String token = PrefrenceUtil.getInstance(mContext).getString(ConShare.TOKEN, "");
        String visitorType = TextUtils.isEmpty(token) ? "0" : "1";
        String cookieFinger = String.valueOf(System.currentTimeMillis());
        String sourceType = "1";
        model.getVisitors("20", visitorType, cookieFinger, materialType, sourceType, itemShopId, skuId, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {

            }
        });
    }
}
