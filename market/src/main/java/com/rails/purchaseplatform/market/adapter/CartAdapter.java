package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketCartBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 购物车列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/10
 */
public class CartAdapter extends BaseRecyclerAdapter<CartBean, ItemMarketCartBinding> {

    private SparseBooleanArray selects;

    public CartAdapter(Context context) {
        super(context);
        selects = new SparseBooleanArray();
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_cart;
    }

    @Override
    protected void onBindItem(ItemMarketCartBinding binding, CartBean cartBean, int position) {
        binding.setCart(cartBean);

        if (selects.get(position))
            binding.imgLeft.setSelected(true);
        else
            binding.imgLeft.setSelected(false);

        binding.imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selects.get(position)) {
                    selects.put(position, false);
                } else
                    selects.put(position, true);
                notifyItemChanged(position);
            }
        });


        CartSubAdapter adapter = new CartSubAdapter(mContext);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.setAdapter(adapter);


        ArrayList<ProductBean> productBeans = new ArrayList<>();
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());
        adapter.update(productBeans, true);
    }

}
