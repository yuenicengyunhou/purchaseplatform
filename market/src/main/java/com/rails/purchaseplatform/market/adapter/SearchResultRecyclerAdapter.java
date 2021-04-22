package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.BaseItemAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultBinding;

public class SearchResultRecyclerAdapter extends BaseRecyclerAdapter<BaseItemAttribute, ItemSearchResultBinding> {

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
    protected void onBindItem(ItemSearchResultBinding binding, BaseItemAttribute baseItemAttribute, int position) {
        binding.setItemAttribute(baseItemAttribute);
//        binding.ivIcon.setImageURI(searchResultBean.getIconUrl());
//        binding.tvName.setText(searchResultBean.getName());
//        binding.tvShop.setText(searchResultBean.getShop());
//        binding.tvPrice.setText(searchResultBean.getPrice());
        binding.tvPrice.setText(String.valueOf(baseItemAttribute.getSellPrice()));
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("platformId", 20L);
            bundle.putLong("itemId", baseItemAttribute.getItemId());
            bundle.putInt("skuId", baseItemAttribute.getSkuId());
            bundle.putInt("cid", baseItemAttribute.getCid());
            bundle.putString("keyword", baseItemAttribute.getSkuName());
            bundle.putLong("shopId", baseItemAttribute.getShopId());
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle).navigation();
        });

    }
}
