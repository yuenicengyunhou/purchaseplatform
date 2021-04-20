package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.BaseItemAttribute;
import com.rails.lib_data.contract.SearchContract;
import com.rails.lib_data.contract.SearchItemPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByProductBinding;

import java.util.ArrayList;


/**
 * 搜索结果 - 按商品搜索
 */
public class SearchResultByProductFragment extends LazyFragment<FragmentSearchResultByProductBinding> implements
        SearchContract.SearchItemView {

    final private String TAG = SearchResultByProductFragment.class.getSimpleName();

    private String mSearchKey;

    private SearchResultRecyclerAdapter mAdapter;
    private SearchItemPresenterImpl mPresenter;

    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key", "");

        mPresenter = new SearchItemPresenterImpl(this.getActivity(), this);
        mPresenter.getItemListWithKeywordOnly(true, 1, 20L, mSearchKey);

        mAdapter = new SearchResultRecyclerAdapter(this.getContext());
        binding.brvProductSearchResult.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.brvProductSearchResult.setAdapter(mAdapter);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getItemListWithKeywordOnly(ArrayList<BaseItemAttribute> baseItemAttributes, boolean hasMore, boolean isClear) {
        mAdapter.update(baseItemAttributes, isClear);
    }
}
