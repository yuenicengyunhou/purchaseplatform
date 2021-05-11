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
        binding.tvPrice.setText(String.format("%.2f", itemAttribute.getSellPrice()));
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("itemId", String.valueOf(itemAttribute.getItemId()));
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle).navigation();
        });

    }
}
