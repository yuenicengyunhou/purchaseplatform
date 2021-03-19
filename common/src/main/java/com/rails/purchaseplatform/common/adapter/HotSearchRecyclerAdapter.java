package com.rails.purchaseplatform.common.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.common.databinding.ItemHotSearchBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

public class HotSearchRecyclerAdapter extends BaseRecyclerAdapter<HotSearchBean, ItemHotSearchBinding> {

    private Context mContext;

    public HotSearchRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_hot_search;
    }

    @Override
    protected void onBindItem(ItemHotSearchBinding binding, HotSearchBean hotSearchBean, int position) {
        binding.setHotSearch(hotSearchBean);
        binding.tvSearchItem.setText(hotSearchBean.getSearchItem());
        binding.tvSearchItem.setOnClickListener(v -> ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).navigation());
    }

}
