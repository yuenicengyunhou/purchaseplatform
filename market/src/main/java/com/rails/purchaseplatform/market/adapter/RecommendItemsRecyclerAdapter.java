package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemRecommendItemsBinding;

public class RecommendItemsRecyclerAdapter extends BaseRecyclerAdapter<RecommendItemsBean, ItemRecommendItemsBinding> {

    private Context mContext;
    private String mMaterialType;


    public RecommendItemsRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }


    public RecommendItemsRecyclerAdapter(Context context, String materialType) {
        super(context);
        mContext = context;
        mMaterialType = materialType;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_recommend_items;
    }

    @Override
    protected void onBindItem(ItemRecommendItemsBinding binding, RecommendItemsBean recommendItemsBean, int position) {
        binding.setRecommend(recommendItemsBean);
        binding.tvPrice.setText(String.format("%.2f", Double.parseDouble(recommendItemsBean.getPrice())));
        if (mMaterialType.equals("1")) { // 专用物资不显示商品价格
            binding.llPrice.setVisibility(View.GONE);
        }
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
