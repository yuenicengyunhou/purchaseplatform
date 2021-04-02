package com.rails.purchaseplatform.market.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.SearchResultByShopBean;
import com.rails.lib_data.contract.SearchResultByShopPresenterImpl;
import com.rails.lib_data.contract.SearchResultContract;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.adapter.SearchResultByShopAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByShopBinding;

import java.util.ArrayList;

public class SearchResultByShopFragment extends LazyFragment<FragmentSearchResultByShopBinding> implements SearchResultContract.SearchResultByShopView {

    final private String TAG = SearchResultByShopFragment.class.getSimpleName();

    private SearchResultByShopAdapter mAdapter;
    private SearchResultByShopPresenterImpl mPresenter;

    @Override
    protected void loadData() {
        mAdapter = new SearchResultByShopAdapter(this.getContext());
        mPresenter = new SearchResultByShopPresenterImpl(this.getActivity(), this);
        binding.brvSearchResultByShopRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvSearchResultByShopRecycler.setAdapter(mAdapter);
        mPresenter.getSearchResultByShop(false, 1);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getSearchResultByShop(ArrayList<SearchResultByShopBean> searchResultByShopBeans, boolean hasMore, boolean isClear) {
        mAdapter.update(searchResultByShopBeans, isClear);
    }
}
