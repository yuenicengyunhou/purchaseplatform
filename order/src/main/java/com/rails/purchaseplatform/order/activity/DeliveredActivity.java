package com.rails.purchaseplatform.order.activity;


import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order_details.DeliveredAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityDeliveredBinding;

import java.util.ArrayList;

@Route(path = ConRoute.ORDER.ORDER_DELIVER)
public class DeliveredActivity extends ToolbarActivity<ActivityDeliveredBinding> implements OrderContract.OrderView {


    private String orderNo;
    private DeliveredAdapter adapter;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        orderNo = extras.getString("orderNo", "");
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar
                .setTitleBar(R.string.deliveredFiles)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);


        adapter = new DeliveredAdapter(this);
        barBinding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        barBinding.empty.setDescEmpty(R.string.order_empty).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        barBinding.recycler.setAdapter(adapter);
        barBinding.recycler.setEmptyView(barBinding.empty);

        OrderPresenterImpl presenter = new OrderPresenterImpl(this, this);
        presenter.getDelivered(orderNo);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void loadDeliveredFileList(ArrayList<DeliveredFile> list) {
        if (null != adapter) {
            adapter.update(list, true);
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean firstPage, int totalCount) {

    }

    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {

    }
}