package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultByShopBinding;

import java.util.ArrayList;

public class SearchResultByShopAdapter extends BaseRecyclerAdapter<ShopAttribute, ItemSearchResultByShopBinding> {

    private Context mContext;

    public SearchResultByShopAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result_by_shop;
    }

    @Override
    protected void onBindItem(ItemSearchResultByShopBinding binding, ShopAttribute shopAttribute, int position) {
        binding.setShopAttr(shopAttribute);
        Glide.with(mContext).load("https:" + shopAttribute.getShopPicture()).into(binding.ratioImage);
        binding.llShop.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("shopInfoId", String.valueOf(shopAttribute.getShopId()));
            ARouter.getInstance().build(ConRoute.MARKET.SHOP_DETAILS).with(bundle).navigation();
        });

        SearchResultByShopProductsAdapter adapter = new SearchResultByShopProductsAdapter(mContext);
        binding.brvProductRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.brvProductRecycler.setAdapter(adapter);
        adapter.update((ArrayList<ItemAttribute>) shopAttribute.getItems(), true);
    }
}
