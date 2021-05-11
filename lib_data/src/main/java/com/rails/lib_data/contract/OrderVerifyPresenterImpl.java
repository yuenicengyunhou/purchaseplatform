package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.model.OrderVerifyModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 订单确认单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/28
 */
public class OrderVerifyPresenterImpl extends BasePresenter<OrderVerifyContract.OrderVerifyView> implements OrderVerifyContract.OrderVerifyPresenter {

    private OrderVerifyModel model;

    public OrderVerifyPresenterImpl(Activity mContext, OrderVerifyContract.OrderVerifyView orderVerifyView) {
        super(mContext, orderVerifyView);
        model = new OrderVerifyModel();
    }

    @Override
    public void getVerifyOrder(String addressId) {

        baseView.showResDialog(R.string.loading);
        model.getOrderVerifyInfo(addressId, new HttpRxObserver<OrderVerifyBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(OrderVerifyBean bean) {
                baseView.dismissDialog();
                if (isCallBack()) {
                    for (CartShopBean shopBean : bean.getCart().getShopList()) {
                        int totalNum = 0;
                        for (CartShopProductBean productBean : shopBean.getSkuList()) {
                            totalNum += productBean.getSkuNum();
                        }
                        shopBean.setTotalNum(totalNum);
                    }
                    baseView.getVerifyOrder(bean);
                }
            }
        });


    }

    @Override
    public void getPurchases() {
        model.getPurchaseCompanys(new HttpRxObserver<ArrayList<OrderPurchaseBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(ArrayList<OrderPurchaseBean> beans) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getPurchases(beans);
            }
        });
    }

    @Override
    public void getOrderToken() {
        model.getOrderToken(new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.getOrderToken(response);
            }
        });
    }

    @Override
    public void commitOrder(String token, String obj) {

        baseView.showResDialog(R.string.loading);
        model.commitOrder(obj, token, new HttpRxObserver<ArrayList<Long>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<Long> datas) {
                baseView.dismissDialog();
                String orderNo = "";
//                Type type = new TypeToken<ArrayList<String>>() {
//                }.getType();
//                ArrayList<String> datas = JsonUtil.parseJson(String.valueOf(data), type);
                if (datas == null)
                    return;
                if (!datas.isEmpty()) {
                    orderNo = String.valueOf(datas.get(0));
                }

                baseView.getResult("提交成功", orderNo);
            }
        });
    }
}
