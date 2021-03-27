package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.CartShopBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderVerifyShopBinding;

/**
 * 核对单店铺列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/27
 */
public class OrderVerifyAdapter extends BaseRecyclerAdapter<CartShopBean, ItemOrderVerifyShopBinding> {


    public OrderVerifyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_verify_shop;
    }

    @Override
    protected void onBindItem(ItemOrderVerifyShopBinding binding, CartShopBean bean, int position) {
        binding.setCart(bean);
    }
}
