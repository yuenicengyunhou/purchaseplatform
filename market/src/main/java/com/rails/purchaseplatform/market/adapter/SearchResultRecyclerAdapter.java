package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultBinding;

//BaseItemAttribute
public class SearchResultRecyclerAdapter extends BaseRecyclerAdapter<ItemAttribute, ItemSearchResultBinding> {

    private Context mContext;
    private String mMaterialType = "0";

    public SearchResultRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public SearchResultRecyclerAdapter(Context context, String materialType) {
        super(context);
        mContext = context;
        mMaterialType = materialType;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result;
    }

    @Override
    protected void onBindItem(ItemSearchResultBinding binding, ItemAttribute itemAttribute, int position) {
        binding.setItemAttribute(itemAttribute);
        if (mMaterialType.equals("1")) { // 值为1就是专用物资，隐藏店铺名称和价格。
            binding.tvShop.setVisibility(View.GONE);
            binding.llPrice.setVisibility(View.GONE);
        }
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
