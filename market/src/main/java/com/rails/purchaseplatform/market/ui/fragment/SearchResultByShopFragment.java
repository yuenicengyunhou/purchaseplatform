package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.lib_data.contract.SearchContract;
import com.rails.lib_data.contract.SearchShopPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultByShopAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByShopBinding;
import com.rails.purchaseplatform.market.ui.activity.SearchResultActivity;

import java.util.ArrayList;


/**
 * 搜索结果 - 按店铺搜索
 */
public class SearchResultByShopFragment extends LazyFragment<FragmentSearchResultByShopBinding> implements
        SearchContract.SearchShopView, SearchResultActivity.OnSortClick {

    final private String TAG = SearchResultByShopFragment.class.getSimpleName();

    private SearchResultByShopAdapter mAdapter;
    private SearchContract.SearchShopPresenter mPresenter;

    private String mSearchKey;
    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private String orderColumn;
    private String orderType;

    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key", "");

        mAdapter = new SearchResultByShopAdapter(this.getContext());
        binding.brvSearchResultByShopRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.brvSearchResultByShopRecycler.setAdapter(mAdapter);
        binding.brvSearchResultByShopRecycler.setEmptyView(binding.empty);


        mPresenter = new SearchShopPresenterImpl(this.getActivity(), this);
        onRefresh();
    }


    @Override
    protected void loadPreVisitData() {

    }

    /**
     * 数据刷新操作
     */
    private void onRefresh() {
        binding.smart.setEnableRefresh(false);
        binding.smart.setOnLoadMoreListener(refreshLayout -> {
            page++;
            notifyData(page, mSearchKey, orderColumn, orderType, false);
        });
        notifyData(page, mSearchKey, orderColumn, orderType, true);
    }


    /**
     * 请求网络
     *
     * @param orderColumn
     * @param orderType
     * @param keyWord
     * @param page
     * @param isDialog
     */
    void notifyData(int page, String keyWord, String orderColumn, String orderType, boolean isDialog) {
        mPresenter.getShopListWithKeywordOnly(page, 30, keyWord, orderColumn, orderType, isDialog);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getShopListWithKeywordOnly(ArrayList<ShopAttribute> beans, boolean hasMore, boolean isClear) {
        mAdapter.update(beans, isClear);
        binding.smart.finishLoadMore();
    }

    @Override
    public void sort(String orderColumn, String orderType, String keyword, String cid) {
        this.orderColumn = orderColumn;
        this.orderType = orderType;
        this.mSearchKey = keyword;
        page = DEF_PAGE;
        notifyData(page, mSearchKey, orderColumn, orderType, true);
    }

    @Override
    public ArrayList<SearchFilterBean> getFilterData() {
        return null;
    }
}
