package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderRecyclerBinding;

public class OrderRecyclerAdapter extends BaseRecyclerAdapter<OrderItemBean, ItemOrderRecyclerBinding> {
    private Context mContext;


    public OrderRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_recycler;
    }

    @Override
    protected void onBindItem(ItemOrderRecyclerBinding binding, OrderItemBean orderItemBean, int position) {
        binding.setOrderItemBean(orderItemBean);

    }
}
