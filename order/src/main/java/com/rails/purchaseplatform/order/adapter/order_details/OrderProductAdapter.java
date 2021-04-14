package com.rails.purchaseplatform.order.adapter.order_details;

import android.content.Context;

import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailGoodBinding;

public class OrderProductAdapter extends BaseRecyclerAdapter<ProductBean, ItemOrderDetailGoodBinding> {
    public OrderProductAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return  R.layout.item_order_detail_good;
    }

    @Override
    protected void onBindItem(ItemOrderDetailGoodBinding binding, ProductBean productBean, int position) {
        binding.setProduct(productBean);
    }
}
