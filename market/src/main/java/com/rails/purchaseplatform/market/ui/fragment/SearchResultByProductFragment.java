package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.ItemAttribute;
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
    private String mCid;

    private SearchResultRecyclerAdapter mAdapter;
    private SearchItemPresenterImpl mPresenter;

    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key");
        mCid = bundle.getString("cid");

        mPresenter = new SearchItemPresenterImpl(this.getActivity(), this);

        if (mSearchKey != null && !TextUtils.isEmpty(mSearchKey))
            mPresenter.getItemListWithKeywordOnly(true, 1, 20L, mSearchKey);
        if (mCid != null && !TextUtils.isEmpty(mCid))
            mPresenter.getItemListWithCid(mCid, 1, false);

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
    public void getItemListWithKeywordOnly(ArrayList<ItemAttribute> itemAttributes, boolean hasMore, boolean isClear) {
        mAdapter.update(itemAttributes, isClear);
    }

    @Override
    public void getItemListWithCid(ArrayList<ItemAttribute> results, boolean hasMore, boolean isClear) {
        mAdapter.update(results, isClear);
    }
}
