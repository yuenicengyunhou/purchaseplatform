package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.contract.SearchContract;
import com.rails.lib_data.contract.SearchItemPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByProductBinding;
import com.rails.purchaseplatform.market.ui.activity.SearchResultActivity;

import java.util.ArrayList;


/**
 * 搜索结果 - 按商品搜索
 */
public class SearchResultByProductFragment extends LazyFragment<FragmentSearchResultByProductBinding>
        implements
        SearchContract.SearchItemView,
        SearchResultActivity.OnSortClick,
        SearchResultActivity.OnFilterClick {

    final private String TAG = SearchResultByProductFragment.class.getSimpleName();

    private String mSearchKey;
    private String mCid;

    private SearchResultRecyclerAdapter mAdapter;
    private SearchContract.SearchItemPresenter mPresenter;

    private ArrayList<SearchFilterBean> mSearchFilterList;


    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key");
        mCid = bundle.getString("cid");

        mPresenter = new SearchItemPresenterImpl(this.getActivity(), this);

        if (mSearchKey != null && !TextUtils.isEmpty(mSearchKey))
            mPresenter.getItemListWithKeywordOnly(null, null, mSearchKey, 1, true);
        if (mCid != null && !TextUtils.isEmpty(mCid))
            mPresenter.getItemListWithCid(null, null, mCid, 1, false);

        mAdapter = new SearchResultRecyclerAdapter(this.getContext());
        binding.brvProductSearchResult.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.brvProductSearchResult.setAdapter(mAdapter);
        binding.brvProductSearchResult.setEmptyView(binding.empty);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getItemListWithKeywordOnly(ArrayList<ItemAttribute> itemAttributes, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear) {
        mAdapter.update(itemAttributes, isClear);
        mSearchFilterList = filterResults;
    }

    @Override
    public void getItemListWithCid(ArrayList<ItemAttribute> results, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear) {
        mAdapter.update(results, isClear);
        mSearchFilterList = filterResults;
    }

    @Override
    public void sort(String orderColumn, String orderType, String keyword, String cid) {
        if (keyword != null)
            mPresenter.getItemListWithKeywordOnly(orderColumn, orderType, keyword, 1, false);
        if (cid != null)
            mPresenter.getItemListWithCid(orderColumn, orderType, cid, 1, false);
    }

    @Override
    public ArrayList<SearchFilterBean> getFilterData() {
        return mSearchFilterList;
    }

    @Override
    public void sendFilterData(String[] strings) {
        // TODO: 2021/4/29 发送数据
//        mPresenter.getItemListWithCid();
    }
}
