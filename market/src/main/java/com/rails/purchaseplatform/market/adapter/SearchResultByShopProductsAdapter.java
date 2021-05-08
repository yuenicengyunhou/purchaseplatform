package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultByShopProductBinding;

public class SearchResultByShopProductsAdapter extends BaseRecyclerAdapter<ItemAttribute, ItemSearchResultByShopProductBinding> {

    private Context mContext;

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
        binding.setItemAttr(itemAttribute);
        binding.tvPrice.setText(String.valueOf(itemAttribute.getSellPrice()));
        binding.tvItemShop.setText(itemAttribute.getShopName());
        binding.rcProduct.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("itemId", String.valueOf(itemAttribute.getItemId()));
//            bundle.putInt("skuId", itemAttribute.getSkuId());
//            bundle.putInt("cid", itemAttribute.getCid());
//            bundle.putLong("shopId", itemAttribute.getItemId());
            ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
        });
    }
}