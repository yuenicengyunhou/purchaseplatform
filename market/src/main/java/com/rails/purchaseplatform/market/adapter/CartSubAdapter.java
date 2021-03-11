package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketCartSubBinding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/11
 */
public class CartSubAdapter extends BaseRecyclerAdapter<ProductBean, ItemMarketCartSubBinding> {
    public CartSubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_cart_sub;
    }

    @Override
    protected void onBindItem(ItemMarketCartSubBinding binding, ProductBean productBean, int position) {

    }
}
