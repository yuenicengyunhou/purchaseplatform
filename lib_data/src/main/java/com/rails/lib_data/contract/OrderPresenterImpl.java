package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.model.OrderModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {

    private OrderModel model;

    public OrderPresenterImpl(Activity mContext, OrderContract.OrderView orderView) {
        super(mContext, orderView);
        model = new OrderModel();
    }

    @Override
    public void getOrder(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        Type type = new TypeToken<ArrayList<OrderBean>>() {
        }.getType();
        ArrayList<OrderBean> beans = JsonUtil.parseJson(mContext, "orderList.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getOrder(beans, false, isClear);
        }
    }
}
