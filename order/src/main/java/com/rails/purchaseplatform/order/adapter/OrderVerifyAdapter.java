package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.CartShopBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderVerifyShopBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

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
        binding.tvSubtotal.setText(DecimalUtil
                .formatStrColor(mContext.getResources().getString(R.string.order_verify_subtotal),
                        "¥" + bean.getSubtotalPrice(), "",
                        mContext.getResources().getColor(R.color.font_red)));

        OrderVerifySubAdapter adapter = new OrderVerifySubAdapter(mContext);
        binding.recycler.setAdapter(adapter);
        adapter.update((ArrayList) bean.getSkuList(), true);
    }

    @Override
    protected void onBindView(ItemOrderVerifyShopBinding binding) {
        super.onBindView(binding);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
    }
}
