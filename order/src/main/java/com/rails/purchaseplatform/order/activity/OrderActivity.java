package com.rails.purchaseplatform.order.activity;


import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.order.adapter.OrderRecyclerAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityOrderBinding;

import java.util.ArrayList;

/**
 * 采购单列表页面
 * <p>
 * -1- 列表(采购单订单)和子列表(订单内商品)
 * -2- 搜索
 * -3- 筛选
 * -4- PopupWindow切换搜索类型
 * -5- Tab切换我的采购单和全部采购单
 */
@Route(path = ConRoute.ORDER.ORDER_MAIN)
public class OrderActivity extends BaseErrorActivity<ActivityOrderBinding> implements OrderContract.OrderView {

    private OrderRecyclerAdapter mAdapter;
    private OrderContract.OrderPresenter mPresenter;


    @Override
    protected void initialize(Bundle bundle) {
        mAdapter = new OrderRecyclerAdapter(this);
        mPresenter = new OrderPresenterImpl(this, this);
        binding.brvOrderRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvOrderRecycler.setAdapter(mAdapter);
        mPresenter.getOrder(false, 1);
    }

    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onClick() {
        super.onClick();
        binding.ibBack.setOnClickListener(v -> finish());
        binding.ibFilter.setOnClickListener(v -> Toast.makeText(this, "暂时没有内容哦", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void getOrder(ArrayList<OrderBean> orderBeans, boolean hasMore, boolean isClear) {
        mAdapter.update(orderBeans, true);
    }
}