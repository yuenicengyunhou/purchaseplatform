package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
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
public class CartAdapter extends BaseRecyclerAdapter<CartShopBean, ItemMarketCartBinding> {

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
    protected void onBindItem(ItemMarketCartBinding binding, CartShopBean cartShopBean, int position) {
        binding.setCart(cartShopBean);

        if (binding.recycler.getAdapter() == null) {
            CartSubAdapter adapter = new CartSubAdapter(mContext);
            binding.recycler.setAdapter(adapter);
            adapter.setListener(new PositionListener<CartShopProductBean>() {
                @Override
                public void onPosition(CartShopProductBean bean, int position) {
                    boolean isCheck = isAllChecked(cartShopBean);
                    cartShopBean.isSel.set(isCheck);
                }

            });
            adapter.update((ArrayList) cartShopBean.getSkuList(), true);
        }

        binding.imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll(cartShopBean, binding.imgLeft.isChecked());
            }
        });
    }


    /**
     * 商铺全选
     *
     * @param cartShopBean
     * @param isChecked
     */
    private void checkAll(CartShopBean cartShopBean, boolean isChecked) {
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
                bean.isSel.set(isChecked);
            }
        } catch (Exception e) {
        }

    }


    /**
     *
     */
    private boolean isAllChecked(CartShopBean cartShopBean) {
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
                if (bean.isSel.get()) {
                    continue;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
        }

        return true;
    }


    @Override
    protected void onBindView(ItemMarketCartBinding binding) {
        super.onBindView(binding);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.addItemDecoration(new SpaceDecoration(mContext, 1, R.color.line_gray));
    }
}
