package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.shop.ItemSkuBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultBinding;
import com.rails.purchaseplatform.market.databinding.ItemShopSkuBinding;

import java.util.List;

public class ShopAdapter extends BaseRecyclerAdapter<ResultListBean, ItemShopSkuBinding> {

    private Context mContext;

    public ShopAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_shop_sku;
    }

    @Override
    protected void onBindItem(ItemShopSkuBinding binding, ResultListBean bean, int position) {
        binding.setBean(bean);
        List<ItemSkuBean> item_sku = bean.getItem_sku();
        int skuId = 0;
        String skuName = "";
        double sellPrice = 0.0;
        String url = "";
        if (!item_sku.isEmpty()) {
            ItemSkuBean skuBean = item_sku.get(0);
            skuName = skuBean.getSkuName();
//            sellPrice = skuBean.getSellPrice();
            skuId = skuBean.getSkuId();
            url = "https:" + skuBean.getPictureUrl();
        }
        String shopName = bean.getShopName();
//        binding.tvName.setText(skuName);
//        binding.tvShop.setText(shopName);
//        binding.tvPrice.setText(String.valueOf(sellPrice));
//        Log.e("WQ", "url==" + url);
//        Glide.with(mContext).load(url).into(binding.ivIcon);
//        binding.tvPrice.setText(String.valueOf(baseItemAttribute.getPrice()));
        int finalSkuId = skuId;
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("platformId", 20L);
            bundle.putLong("itemId", bean.getItemId());
            bundle.putInt("skuId", finalSkuId);
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle).navigation();
        });

    }


}
