package com.rails.purchaseplatform.common.adapter;

import android.content.Context;

import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

public class HotSearchRecyclerAdapter extends BaseRecyclerAdapter<HotSearchBean, ActivitySearchXBinding> {
    public HotSearchRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_hot_search;
    }

    @Override
    protected void onBindItem(ActivitySearchXBinding binding, HotSearchBean hotSearchBean, int position) {

    }
}
