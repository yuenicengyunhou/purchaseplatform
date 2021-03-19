package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.RecommendItemsBean;
import com.rails.lib_data.contract.RecommendItemsContract;
import com.rails.lib_data.contract.RecommendItemsPresenterImpl;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.adapter.RecommendItemsRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityItemDetailsBinding;

import java.util.ArrayList;

public class ItemDetailsActivity extends BaseErrorActivity<ActivityItemDetailsBinding> implements RecommendItemsContract.RecommendItemsView {

    private RecommendItemsRecyclerAdapter recommendItemsRecyclerAdapter;
    private RecommendItemsContract.RecommendItemsPresenter recommendItemsPresenter;


    @Override
    protected void initialize(Bundle bundle) {
        recommendItemsRecyclerAdapter = new RecommendItemsRecyclerAdapter(this);
        recommendItemsPresenter = new RecommendItemsPresenterImpl(this, this);
        binding.recyclerRecommendItems.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recyclerRecommendItems.setAdapter(recommendItemsRecyclerAdapter);
        recommendItemsPresenter.getRecommendItems(false, 1);
    }

    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void getRecommendItems(ArrayList<RecommendItemsBean> recommendItemsBeans, boolean hasMore, boolean isClear) {
        recommendItemsRecyclerAdapter.update(recommendItemsBeans, false);
    }
}
