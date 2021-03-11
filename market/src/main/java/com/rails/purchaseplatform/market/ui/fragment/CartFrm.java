package com.rails.purchaseplatform.market.ui.fragment;

import com.rails.lib_data.bean.CartBean;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.adapter.CartAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCartBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class CartFrm extends LazyFragment<FrmCartBinding> {


    CartAdapter cartAdapter;

    @Override
    protected void loadData() {
        cartAdapter = new CartAdapter(getActivity());
        binding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.cartRecycler.setAdapter(cartAdapter);

        ArrayList<CartBean> beans = new ArrayList<>();
        beans.add(new CartBean("养生堂"));
        beans.add(new CartBean("养生堂"));
        beans.add(new CartBean("养生堂"));
        beans.add(new CartBean("养生堂"));
        cartAdapter.update(beans, true);

    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }
}
