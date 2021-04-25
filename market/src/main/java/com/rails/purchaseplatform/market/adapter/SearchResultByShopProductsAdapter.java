package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.content.Intent;

import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultByShopProductBinding;
import com.rails.purchaseplatform.market.ui.activity.ProductDetailsActivity;

import java.util.ArrayList;

public class SearchResultByShopProductsAdapter extends BaseRecyclerAdapter<ItemAttribute, ItemSearchResultByShopProductBinding> {

    private Context mContext;
    private ArrayList<ItemAttribute> mBeans;

    public SearchResultByShopProductsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result_by_shop_product;
    }

    @Override
    protected void onBindItem(ItemSearchResultByShopProductBinding binding, ItemAttribute itemAttribute, int position) {
        binding.setItemAttr(mBeans.get(position));
        binding.rcProduct.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ProductDetailsActivity.class));
        });
    }

    public void setArrayList(ArrayList<ItemAttribute> list) {
        mBeans = list;
    }
}
