package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketCartSubBinding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/11
 */
public class CartSubAdapter extends BaseRecyclerAdapter<CartShopProductBean, ItemMarketCartSubBinding> {
    public CartSubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_cart_sub;
    }

    @Override
    protected void onBindItem(ItemMarketCartSubBinding binding, CartShopProductBean productBean, int position) {
        binding.setProduct(productBean);

        binding.imgLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (positionListener != null) {
                    productBean.isSel.set(isChecked);
                    positionListener.onPosition(productBean, position);
                }

            }
        });
    }
}
