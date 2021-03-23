package com.rails.purchaseplatform.common.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.lib_data.contract.HotSearchContract;
import com.rails.lib_data.contract.HotSearchPresenterImpl;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.HotSearchRecyclerAdapter;
import com.rails.purchaseplatform.common.adapter.SearchHistoryFlowAdapter;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面，点击首页搜索跳转到此页面
 * 1 - 是否需要自动完成
 * 2 - 展示搜索历史（最近10条，存储在本地）
 * 3 - 展示热门搜索（由服务器返回）
 * 4 - PopWindow，用来更改搜索商品或者搜索店铺
 * 5 -
 */
@Route(path = "/common/SearchActivityX")
public class SearchActivityX extends BaseErrorActivity<ActivitySearchXBinding> implements HotSearchContract.HotSearchView {
    final private String TAG = SearchActivityX.class.getName();

    private final List<String> mHistorySearchList = new ArrayList<>();

    {
        mHistorySearchList.add("耳机");
        mHistorySearchList.add("手机");
        mHistorySearchList.add("飞利浦剃须刀");
        mHistorySearchList.add("A4打印纸");
        mHistorySearchList.add("苏泊尔电水壶");
        mHistorySearchList.add("中性笔黑色");
        mHistorySearchList.add("漫步者");
        mHistorySearchList.add("Mate40Pro");
        mHistorySearchList.add("斩切刀");
        mHistorySearchList.add("U盘");
        mHistorySearchList.add("老A工具");
        mHistorySearchList.add("胡姬花花生油");
    }

    private SearchHistoryFlowAdapter mSearchHistoryFlowAdapter;
    private FlowLayoutManager mFlowLayoutManager;

    private HotSearchContract.HotSearchPresenter mHotSearchPresenter;
    private HotSearchRecyclerAdapter mHotSearchRecyclerAdapter;


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
    protected void initialize(Bundle bundle) {
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> SearchActivityX.this.finish());

        mSearchHistoryFlowAdapter = new SearchHistoryFlowAdapter(this, mHistorySearchList);
        mFlowLayoutManager = new FlowLayoutManager();
        binding.recyclerSearchHistory.setLayoutManager(mFlowLayoutManager);
        binding.recyclerSearchHistory.addItemDecoration(new SpaceItemDecoration(20));
        binding.recyclerSearchHistory.setAdapter(mSearchHistoryFlowAdapter);

        mHotSearchRecyclerAdapter = new HotSearchRecyclerAdapter(this);
        mHotSearchPresenter = new HotSearchPresenterImpl(this, this);
        binding.recyclerHotSearch.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recyclerHotSearch.setAdapter(mHotSearchRecyclerAdapter);
        mHotSearchPresenter.getHotSearch(false, 1);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void getHotSearch(ArrayList<HotSearchBean> hotSearchBeans, boolean hasMore, boolean isClear) {
        mHotSearchRecyclerAdapter.update(hotSearchBeans, true);
    }
}
