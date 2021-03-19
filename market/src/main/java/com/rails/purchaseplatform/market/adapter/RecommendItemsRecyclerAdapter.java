package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.widget.Toast;

import com.rails.lib_data.bean.RecommendItemsBean;
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
        binding.llItems.setOnClickListener(v -> Toast.makeText(mContext, "HELLO~~~~~", Toast.LENGTH_SHORT).show());
    }
}
