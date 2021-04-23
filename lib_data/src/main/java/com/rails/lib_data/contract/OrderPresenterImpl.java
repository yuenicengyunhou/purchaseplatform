package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.OrderModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {

    private final OrderModel model;
    private final String accountId;

    public OrderPresenterImpl(Activity mContext, OrderContract.OrderView orderView) {
        super(mContext, orderView);
        model = new OrderModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        accountId = bean.getId();
    }

    @Override
    public void getOrder(boolean isDialog, int page, int queryType, String squence, String content) {
        if (isDialog) {
            baseView.showResDialog(R.string.loading);
        }
        model.getPurchasePageList(20, accountId, queryType, 2, squence, content, page, new HttpRxObserver<ListBeen<OrderInfoBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ListBeen<OrderInfoBean> response) {
                boolean lastPage = response.isLastPage();
                if (lastPage) {
                    ToastUtil.show(mContext, "最后一页啦");
                }
                baseView.dismissDialog();
                ArrayList<OrderInfoBean> list = response.getList();
                boolean isClear = page <= 1;
                baseView.getOrder(list, false, isClear);

            }
        });
    }
}
