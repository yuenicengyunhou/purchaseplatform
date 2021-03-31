package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemChildOrderRecyclerBinding;

import java.util.List;

public class OrderChildRecyclerAdapter extends BaseRecyclerAdapter<OrderItemBean, ItemChildOrderRecyclerBinding> {

    public OrderChildRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_child_order_recycler;
    }

    @Override
    protected void onBindItem(ItemChildOrderRecyclerBinding binding, OrderItemBean orderItemBean, int position) {
        binding.setChildOrder(orderItemBean);
        double price = Double.parseDouble(orderItemBean.getItemPrice());
        int count = Integer.parseInt(orderItemBean.getItemCount());
        binding.tvPriceValue.setText(String.valueOf(price * count));
    }

    @Override
    protected void onBindView(ItemChildOrderRecyclerBinding binding) {
        super.onBindView(binding);
    }
}
