package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultBinding;
//BaseItemAttribute
public class SearchResultRecyclerAdapter extends BaseRecyclerAdapter<ItemAttribute, ItemSearchResultBinding> {

    private Context mContext;

    public SearchResultRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result;
    }

    @Override
    protected void onBindItem(ItemSearchResultBinding binding, ItemAttribute itemAttribute, int position) {
        binding.setItemAttribute(itemAttribute);
//        binding.ivIcon.setImageURI(searchResultBean.getIconUrl());
//        binding.tvName.setText(searchResultBean.getName());
//        binding.tvShop.setText(searchResultBean.getShop());
//        binding.tvPrice.setText(searchResultBean.getPrice());
        binding.tvPrice.setText(String.valueOf(itemAttribute.getSellPrice()));
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("platformId", 20L);
            bundle.putLong("itemId", itemAttribute.getItemId());
            bundle.putInt("skuId", itemAttribute.getSkuId());
            bundle.putInt("cid", itemAttribute.getCid());
            bundle.putString("keyword", itemAttribute.getSkuName());
            bundle.putLong("shopId", itemAttribute.getShopId());
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle).navigation();
        });

    }
}
