package com.rails.purchaseplatform.order.adapter.order_details;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.OrderAuditBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailAuditBinding;

public class OrderAuditAdapter extends BaseRecyclerAdapter<OrderAuditBean, ItemOrderDetailAuditBinding> {
    public OrderAuditAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_detail_audit;
    }

    @Override
    protected void onBindItem(ItemOrderDetailAuditBinding binding, OrderAuditBean orderAuditBean, int position) {
        binding.setAudit(orderAuditBean);
        if (position == 0) {
            binding.upLine.setVisibility(View.INVISIBLE);
        }else {
            binding.upLine.setVisibility(View.VISIBLE);
        }
        if (position == getItemCount() - 1) {
            binding.downLine.setVisibility(View.INVISIBLE);
        } else {
            binding.downLine.setVisibility(View.VISIBLE);
        }
    }
}
