package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.NavigationBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketNavigationBinding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class NavigationAdapter extends BaseRecyclerAdapter<NavigationBean, ItemMarketNavigationBinding> {
    public NavigationAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_navigation;
    }

    @Override
    protected void onBindItem(ItemMarketNavigationBinding binding, NavigationBean navigationBean, int position) {
        binding.setSub(navigationBean);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null) {
                    positionListener.onPosition(navigationBean, position);
                }
            }
        });
    }

}
