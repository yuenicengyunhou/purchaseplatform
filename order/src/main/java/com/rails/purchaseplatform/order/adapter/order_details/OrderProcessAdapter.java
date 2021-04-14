package com.rails.purchaseplatform.order.adapter.order_details;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.OrderProcessBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailProcessBinding;

public class OrderProcessAdapter extends BaseRecyclerAdapter<OrderProcessBean, ItemOrderDetailProcessBinding> {
    public OrderProcessAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_detail_process;
    }

    @Override
    protected void onBindItem(ItemOrderDetailProcessBinding binding, OrderProcessBean orderProcessBean, int position) {
        binding.setProcess(orderProcessBean);
        if (position == 0) {
            binding.leftLine.setVisibility(View.INVISIBLE);
        }
        if (position == mDataSource.size() - 1) {
            binding.rightLine.setVisibility(View.INVISIBLE);
        }
    }
}
