package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemRecommendItemsBinding;

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
        binding.tvPrice.setText(String.format("%.2f", Double.parseDouble(recommendItemsBean.getPrice())));
        binding.llItems.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("itemId", String.valueOf(recommendItemsBean.getItemId()));
            ARouter.getInstance()
                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
                    .with(bundle)
                    .navigation();
        });
    }
}
