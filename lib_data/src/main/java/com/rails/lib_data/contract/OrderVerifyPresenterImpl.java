package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

/**
 * 订单确认单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/28
 */
public class OrderVerifyPresenterImpl extends BasePresenter<OrderVerifyContract.OrderVerifyView> implements OrderVerifyContract.OrderVerifyPresenter {


    public OrderVerifyPresenterImpl(Activity mContext, OrderVerifyContract.OrderVerifyView orderVerifyView) {
        super(mContext, orderVerifyView);
    }

    @Override
    public void getVerifyOrder() {

        if (isCallBack()) {
            OrderVerifyBean bean = JsonUtil.parseJson(mContext, "order_verify.json", OrderVerifyBean.class);
            baseView.getVerifyOrder(bean);
        }


    }
}
