package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.ListVO;
import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.OrderParentBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.OrderModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {

    private OrderModel model;
    private long accountId;

    public OrderPresenterImpl(Activity mContext, OrderContract.OrderView orderView) {
        super(mContext, orderView);
        model = new OrderModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        String id = bean.getId();
        if (null == id) {
            ToastUtil.show(mContext, "用户信息错误");
            return;
        }
        accountId = Long.parseLong(id);
    }

    @Override
    public void getOrder(boolean isDialog, int page, int queryType) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

//        model.getPurchasePageList(20, accountId, queryType, 2, new HttpRxObserver<ListVO<OrderInfoBean>>() {
//            @Override
//            protected void onError(ErrorBean e) {
//
//            }
//
//            @Override
//            protected void onSuccess(ListVO<OrderInfoBean> response) {
//
//            }
//        });

        Type type = new TypeToken<ArrayList<OrderParentBean>>() {
        }.getType();
        ArrayList<OrderParentBean> beans = JsonUtil.parseJson(mContext, "orderList.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getOrder(beans, false, isClear);
        }
    }
}
