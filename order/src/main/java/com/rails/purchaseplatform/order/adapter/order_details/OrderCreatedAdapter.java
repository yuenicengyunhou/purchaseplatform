package com.rails.purchaseplatform.order.adapter.order_details;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.OrderCreatedBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailCreatedBinding;

public class OrderCreatedAdapter extends BaseRecyclerAdapter<OrderCreatedBean, ItemOrderDetailCreatedBinding> {
    public OrderCreatedAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_detail_created;
    }

    @Override
    protected void onBindItem(ItemOrderDetailCreatedBinding binding, OrderCreatedBean orderCreatedBean, int position) {
        binding.setOrder(orderCreatedBean);
        if (position == mDataSource.size() - 1) {
            binding.divider.setVisibility(View.INVISIBLE);
        }
    }
}
