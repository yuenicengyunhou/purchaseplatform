package com.rails.purchaseplatform.market.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.activity.SearchActivityX;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultAdapter;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivitySearchResultBinding;

import java.util.List;

/**
 * 搜索结果页
 * 1 - 展示搜索结果
 * 2 - 按照所选类型排序（综合、价格、时间、销量）
 * 3 - 可改变搜索结果展示方式（图片GridView，列表ListView）
 * 4 - 点击商品进入商品详情页面
 * 5 - 取消搜索类型时，应退回到搜索页面并设焦点在输入框
 * 6 - 展示搜索结果时，跳转到此页面之前应该保存本地搜索记录
 * 7 - 下拉刷新搜索结果，上拉加载更多搜索结果
 */
@Route(path = "/market/SearchResultActivity")
public class SearchResultActivity extends BaseErrorActivity<ActivitySearchResultBinding> {

    private List mSearchResultDataList;

    private SearchResultRecyclerAdapter mSearchResultRecyclerAdapter;


    private int mModuleFlag = 0;

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

        mSearchResultRecyclerAdapter = new SearchResultRecyclerAdapter(this);
        binding.searchResultRecycler.setAdapter(mSearchResultRecyclerAdapter);
        mSearchResultRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onClick() {
        super.onClick();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> SearchResultActivity.this.finish());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
