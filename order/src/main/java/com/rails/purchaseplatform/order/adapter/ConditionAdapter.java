package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemConditionBinding;


public class ConditionAdapter extends BaseRecyclerAdapter<BuyerBean, ItemConditionBinding> {
    public ConditionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_condition;
    }

    @Override
    protected void onBindItem(ItemConditionBinding binding, BuyerBean buyerBean, int position) {
        String realName = buyerBean.getAccountName();
        String supplierName = buyerBean.getSupplierName();
        String name = null == supplierName ? realName : supplierName;
        binding.tvName.setText(name);

        binding.tvName.setOnClickListener(v -> {
            if (null != positionListener) {
                positionListener.onPosition(buyerBean, position);
            }
        });
    }


}
