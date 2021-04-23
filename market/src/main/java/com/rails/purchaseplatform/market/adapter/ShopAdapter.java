package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.bean.forAppShow.BaseItemAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultBinding;

public class ShopAdapter extends BaseRecyclerAdapter<SearchResultBean, ItemSearchResultBinding> {

    private Context mContext;

    public ShopAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result;
    }

    @Override
    protected void onBindItem(ItemSearchResultBinding binding, SearchResultBean baseItemAttribute, int position) {
        BaseItemAttribute baseItemAttribute1 = new BaseItemAttribute();

//        binding.setItemAttribute(baseItemAttribute);
//        binding.ivIcon.setImageURI(baseItemAttribute.getIconUrl().);
        binding.tvName.setText(baseItemAttribute.getName());
        binding.tvShop.setText(baseItemAttribute.getShop());
        binding.tvPrice.setText(baseItemAttribute.getPrice());
        binding.tvPrice.setText(String.valueOf(baseItemAttribute.getPrice()));
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("platformId", 20L);
//            bundle.putLong("itemId", baseItemAttribute.get());
//            bundle.putInt("skuId", baseItemAttribute.getSkuId());
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle).navigation();
        });

    }
}
