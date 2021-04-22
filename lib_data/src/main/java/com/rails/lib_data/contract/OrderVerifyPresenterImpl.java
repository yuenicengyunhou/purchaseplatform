package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.model.OrderVerifyModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

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
                    baseView.getVerifyOrder(bean);
                }
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
        model.commitOrder(obj, token, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.getResult(response);
            }
        });
    }
}
