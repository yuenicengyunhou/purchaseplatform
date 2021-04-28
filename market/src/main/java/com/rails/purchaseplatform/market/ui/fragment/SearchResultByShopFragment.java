package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.lib_data.contract.SearchContract;
import com.rails.lib_data.contract.SearchShopPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.adapter.SearchResultByShopAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByShopBinding;
import com.rails.purchaseplatform.market.ui.activity.SearchResultActivity;

import java.util.ArrayList;


/**
 * 搜索结果 - 按店铺搜索
 */
public class SearchResultByShopFragment extends LazyFragment<FragmentSearchResultByShopBinding>
        implements
        SearchContract.SearchShopView,
        SearchResultActivity.OnSortClick {

    final private String TAG = SearchResultByShopFragment.class.getSimpleName();

    private SearchResultByShopAdapter mAdapter;
    private SearchContract.SearchShopPresenter mPresenter;

    private String mSearchKey;

    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key", "");

        mPresenter = new SearchShopPresenterImpl(this.getActivity(), this);
        mPresenter.getShopListWithKeywordOnly(
                20L, 1L, false,
                1, 30, mSearchKey,
                null, null, false);

        mAdapter = new SearchResultByShopAdapter(this.getContext());
        binding.brvSearchResultByShopRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvSearchResultByShopRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getShopListWithKeywordOnly(ArrayList<ShopAttribute> beans, boolean hasMore, boolean isClear) {
        mAdapter.update(beans, isClear);
    }

    @Override
    public void sort(String orderColumn, String orderType, String keyword, String cid) {
        mPresenter.getShopListWithKeywordOnly(
                20L, 1L, false,
                1, 30, mSearchKey,
                orderColumn, orderType, false);
    }
}
