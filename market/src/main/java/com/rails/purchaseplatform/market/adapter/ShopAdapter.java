package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.shop.ItemSkuBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemShopSkuBinding;

import java.util.List;

public class ShopAdapter extends BaseRecyclerAdapter<ResultListBean, ItemShopSkuBinding> {

    public ShopAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_shop_sku;
    }

    @Override
    protected void onBindItem(ItemShopSkuBinding binding, ResultListBean bean, int position) {
        binding.setBean(bean);
        List<ItemSkuBean> item_sku = bean.getItem_sku();
        String skuId = "";
        if (!item_sku.isEmpty()) {
            ItemSkuBean skuBean = item_sku.get(0);
            skuId = skuBean.getSkuId();
        }
        String finalSkuId = skuId;
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("platformId", 20L);
            bundle.putString("itemId", String.valueOf(bean.getItemId()));
            bundle.putString("skuId", finalSkuId);
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle).navigation();
        });

    }


}
