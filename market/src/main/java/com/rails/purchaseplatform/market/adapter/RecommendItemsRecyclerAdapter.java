package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemRecommendItemsBinding;
import com.rails.purchaseplatform.market.ui.activity.ProductDetailsActivity;

public class RecommendItemsRecyclerAdapter extends BaseRecyclerAdapter<RecommendItemsBean, ItemRecommendItemsBinding> {

    private Context mContext;


    public RecommendItemsRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_recommend_items;
    }

    @Override
    protected void onBindItem(ItemRecommendItemsBinding binding, RecommendItemsBean recommendItemsBean, int position) {
        binding.setRecommend(recommendItemsBean);
        // TODO: 2021/3/25 跳转页面时需要传入商品数据
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("platformId", recommendItemsBean.getPlatformId());
            bundle.putString("keyword", recommendItemsBean.getName());
            bundle.putInt("cid", recommendItemsBean.getCid());
            bundle.putLong("shopId", recommendItemsBean.getShopId());
            bundle.putLong("itemId", recommendItemsBean.getItemId());
            bundle.putInt("skuId", recommendItemsBean.getSkuId());
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle)
                    .navigation();
        });
    }
}
