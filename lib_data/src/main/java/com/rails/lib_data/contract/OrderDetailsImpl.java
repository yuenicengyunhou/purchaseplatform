package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.OrderCreatedBean;
import com.rails.lib_data.bean.OrderDetailsBean;
import com.rails.lib_data.bean.OrderProcessBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsImpl extends BasePresenter<OrderDetailsContract.DetailView> implements OrderDetailsContract.DetailPresenter {

    public OrderDetailsImpl(Activity mContext, OrderDetailsContract.DetailView detailView) {
        super(mContext, detailView);
    }

    @Override
    public void getOrderDetailsInfo() {
        OrderDetailsBean bean = JsonUtil.parseJson(mContext, "orderDetail.json", OrderDetailsBean.class);
        baseView.loadOrderDetailsInfo(bean);

        Type type = new TypeToken<ArrayList<OrderProcessBean>>() {
        }.getType();
        ArrayList<OrderProcessBean> processBeans = JsonUtil.parseJson(mContext, "process.json", type);
        baseView.loadProcessInfo(processBeans);

        ArrayList<OrderCreatedBean> createdOrder = bean.getCreatedOrder();
        baseView.loadCreatedInfo(createdOrder);

        baseView.loadAuditInfo(bean.getAudit());
    }

    @Override
    public void getAuditInfo() {

    }
}
