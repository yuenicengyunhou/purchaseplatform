package com.rails.purchaseplatform.market.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.contract.SearchResultContract;
import com.rails.lib_data.contract.SearchResultPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByProductBinding;

import java.util.ArrayList;

public class SearchResultByProductFragment extends LazyFragment<FragmentSearchResultByProductBinding> implements SearchResultContract.SearchResultView {

    final private String TAG = SearchResultByProductFragment.class.getSimpleName();

    private SearchResultRecyclerAdapter mAdapter;
    private SearchResultPresenterImpl mPresenter;

    @Override
    protected void loadData() {
        mAdapter = new SearchResultRecyclerAdapter(this.getContext());
        mPresenter = new SearchResultPresenterImpl(this.getActivity(), this);
        binding.brvProductSearchResult.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.brvProductSearchResult.setAdapter(mAdapter);
        mPresenter.getSearchResult(false, 1);

    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getSearchResult(ArrayList<SearchResultBean> searchResultBeans, boolean hasMore, boolean isClear) {
        mAdapter.update(searchResultBeans, isClear);
    }

    @Override
    public void getProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }
}
