package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultByShopProductBinding;

public class SearchResultByShopProductsAdapter extends BaseRecyclerAdapter<ItemAttribute, ItemSearchResultByShopProductBinding> {

    private Context mContext;
    private String mMaterialType;

    public SearchResultByShopProductsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public SearchResultByShopProductsAdapter(Context context, String materialType) {
        super(context);
        mContext = context;
        mMaterialType = materialType;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result_by_shop_product;
    }

    @Override
    protected void onBindItem(ItemSearchResultByShopProductBinding binding, ItemAttribute itemAttribute, int position) {
        binding.setItemAttr(itemAttribute);
        binding.tvPrice.setText(String.format("%.2f", itemAttribute.getSellPrice()));

        String shopName = itemAttribute.getShopName();
        if (!TextUtils.isEmpty(shopName)) {
            if (shopName.contains("<em>"))
                shopName = shopName.replace("<em>", "");
            if (shopName.contains("</em>"))
                shopName = shopName.replace("</em>", "");
            binding.tvItemShop.setVisibility(View.VISIBLE);
            binding.tvItemShop.setText(shopName);
        } else {
            binding.tvItemShop.setVisibility(View.INVISIBLE);
        }

        if (mMaterialType.equals("1")) {
            binding.tvItemShop.setVisibility(View.GONE);
            binding.llPrice.setVisibility(View.GONE);
        }

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