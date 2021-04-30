package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

    //关键字搜索
    private String mSearchKey;
    //分类Id搜索
    private String mCid;
    private String orderColumn;
    private String orderType;


    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;

    private SearchResultRecyclerAdapter mAdapter;
    private SearchContract.SearchItemPresenter mPresenter;

    private ArrayList<SearchFilterBean> mSearchFilterList;


    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key");
        mCid = bundle.getString("cid");

        mAdapter = new SearchResultRecyclerAdapter(this.getContext());
        binding.brvProductSearchResult.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.brvProductSearchResult.setAdapter(mAdapter);
        binding.brvProductSearchResult.setEmptyView(binding.empty);

        mPresenter = new SearchItemPresenterImpl(this.getActivity(), this);

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
            notifyData(orderColumn, orderType, mSearchKey, mCid, page, false);
        });
        notifyData(orderColumn, orderType, mSearchKey, mCid, page, true);
    }


    /**
     * 请求网络
     *
     * @param orderColumn
     * @param orderType
     * @param keyWord     关键字      （关键子搜索接口）
     * @param cid         分类ID      （分类搜索接口）
     * @param page
     * @param isDialog
     */
    void notifyData(String orderColumn, String orderType, String keyWord, String cid, int page, boolean isDialog) {
        if (!TextUtils.isEmpty(mCid))
            mPresenter.getItemListWithCid(orderColumn, orderType, cid, page, isDialog);
        else {
            mPresenter.getItemListWithKeywordOnly(orderColumn, orderType, keyWord, page, isDialog);
        }
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getItemListWithKeywordOnly(ArrayList<ItemAttribute> itemAttributes, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear) {
        mAdapter.update(itemAttributes, isClear);
        binding.smart.finishLoadMore();
        mSearchFilterList = filterResults;
    }

    @Override
    public void getItemListWithCid(ArrayList<ItemAttribute> results, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear) {
        mAdapter.update(results, isClear);
        binding.smart.finishLoadMore();
        mSearchFilterList = filterResults;
    }

    @Override
    public void sort(String orderColumn, String orderType, String keyword, String cid) {
        page = DEF_PAGE;
        this.orderColumn = orderColumn;
        this.orderType = orderType;
        this.mSearchKey = keyword;
        this.mCid = cid;
        notifyData(orderColumn, orderType, mSearchKey, mCid, page, false);
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
