package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.widget.Toast;

import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.purchaseplatform.common.databinding.ItemOrderStatusBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;


/**
 * 采购单列表页的 筛选弹窗中的 采购单状态的 流布局适配器
 */
public class OrderSearchFilterAdapter extends BaseRecyclerAdapter<OrderStatusBean, ItemOrderStatusBinding> {

    private Context mContext;

    public OrderSearchFilterAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return com.rails.purchaseplatform.common.R.layout.item_order_status;
    }

    @Override
    protected void onBindItem(ItemOrderStatusBinding binding, OrderStatusBean orderStatusBean, int position) {
        binding.setOrderStatus(orderStatusBean);
        binding.rlStatus.setOnClickListener(v -> {
            Toast.makeText(mContext, orderStatusBean.getStatus(), Toast.LENGTH_SHORT).show();
        });
    }
}
