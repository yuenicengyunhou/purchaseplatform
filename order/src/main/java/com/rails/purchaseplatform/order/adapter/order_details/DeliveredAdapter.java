package com.rails.purchaseplatform.order.adapter.order_details;

import android.content.Context;

import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemDeliveredBinding;

public class DeliveredAdapter extends BaseRecyclerAdapter<Object, ItemDeliveredBinding> {

    public DeliveredAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_delivered;
    }

    @Override
    protected void onBindItem(ItemDeliveredBinding binding, Object o, int position) {

    }
}
