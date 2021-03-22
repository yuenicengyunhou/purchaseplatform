package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rails.lib_data.bean.RecommendItemsBean;
import com.rails.lib_data.contract.RecommendItemsContract;
import com.rails.lib_data.contract.RecommendItemsPresenterImpl;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.adapter.RecommendItemsRecyclerAdapter;
import com.rails.purchaseplatform.market.adapter.SectionsPagerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityProductDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends BaseErrorActivity<ActivityProductDetailsBinding> implements RecommendItemsContract.RecommendItemsView {

    private RecommendItemsRecyclerAdapter recommendItemsRecyclerAdapter;
    private RecommendItemsContract.RecommendItemsPresenter recommendItemsPresenter;

    final private String BASE_URL = "http://172.28.20.109:3000/";
    final private String DETAILS = "productInfo";
    final private String LIST = "packingList";
    final private String SERVICE = "serviceOrPartner?service=0";
    final private String RECOMMEND = "serviceOrPartner?service=1";
    final private String[] TAB_URLS = {
            BASE_URL + DETAILS,
            BASE_URL + LIST,
            BASE_URL + SERVICE,
            BASE_URL + RECOMMEND
    };


    @Override
    protected void initialize(Bundle bundle) {
        recommendItemsRecyclerAdapter = new RecommendItemsRecyclerAdapter(this);
        recommendItemsPresenter = new RecommendItemsPresenterImpl(this, this);
        binding.recyclerRecommendItems.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recyclerRecommendItems.setAdapter(recommendItemsRecyclerAdapter);
        recommendItemsPresenter.getRecommendItems(false, 1);

        TabLayout tabLayout = binding.tabDetails;
        ViewPager viewPager = binding.pagerDetails;
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
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
