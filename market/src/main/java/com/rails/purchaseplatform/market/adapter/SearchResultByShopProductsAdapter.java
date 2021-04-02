package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.content.Intent;

import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultByShopProductBinding;
import com.rails.purchaseplatform.market.ui.activity.ProductDetailsActivity;

import java.util.ArrayList;

public class SearchResultByShopProductsAdapter extends BaseRecyclerAdapter<OrderItemBean, ItemSearchResultByShopProductBinding> {

    private Context mContext;
    private ArrayList<OrderItemBean> mBeans;

    public SearchResultByShopProductsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result_by_shop_product;
    }

    @Override
    protected void onBindItem(ItemSearchResultByShopProductBinding binding, OrderItemBean OrderItemBean, int position) {
        binding.setResultByShopProduct(mBeans.get(position));
        binding.llProduct.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ProductDetailsActivity.class));
        });
    }

    public void setArrayList(ArrayList<OrderItemBean> list) {
        mBeans = list;
    }
}
