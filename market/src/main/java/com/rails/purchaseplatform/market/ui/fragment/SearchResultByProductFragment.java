package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.contract.SearchContract;
import com.rails.lib_data.contract.SearchItemPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByProductBinding;
import com.rails.purchaseplatform.market.ui.activity.SearchResultActivity;

import java.util.ArrayList;
import java.util.Objects;


/**
 * 搜索结果 - 按商品搜索
 */
public class SearchResultByProductFragment extends LazyFragment<FragmentSearchResultByProductBinding>
        implements
        SearchContract.SearchItemView,
        SearchResultActivity.OnSortClick,
        SearchResultActivity.OnFilterClick {

    final private String TAG = SearchResultByProductFragment.class.getSimpleName();

    private boolean filtered = false;

    //关键字搜索
    private String mSearchKey;
    //分类Id搜索
    private String mCid;
    private String orderColumn;
    private String orderType;
    private String brands, brandsString,
            categoryAttrValueIds, expandAttrValueIds,
            minPrice, maxPrice;
    private String mMaterialType;


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
        mMaterialType = bundle.getString("materialType");

        mAdapter = new SearchResultRecyclerAdapter(this.getContext(), mMaterialType);
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
            notifyData(mSearchKey, mCid, orderColumn, orderType,
                    brands, brandsString, categoryAttrValueIds, expandAttrValueIds,
                    minPrice, maxPrice, page, 30, false);
        });
        notifyData(mSearchKey, mCid, orderColumn, orderType,
                brands, brandsString, categoryAttrValueIds, expandAttrValueIds,
                minPrice, maxPrice, page, 30, true);
        if (!TextUtils.isEmpty(mSearchKey)) {
            mPresenter.searchRecord("1", mSearchKey);
        }
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
    void notifyData(String keyWord, String cid, String orderColumn, String orderType,
                    String brands, String brandsString, String categoryAttrValueIds, String expandAttrValueIds,
                    String minPrice, String maxPrice, int page, int pageSize, boolean isDialog) {
        if (!TextUtils.isEmpty(mCid)) {
//            mPresenter.getItemListWithCid(orderColumn, orderType, cid, page, isDialog);
            mPresenter.queryItemListByCid(mMaterialType, keyWord, cid, orderColumn, orderType, brands, brandsString,
                    categoryAttrValueIds, expandAttrValueIds, minPrice, maxPrice, page, pageSize, isDialog);
        } else {
//            mPresenter.getItemListWithKeywordOnly(orderColumn, orderType, keyWord, page, isDialog);
            mPresenter.queryItemListByKeyword(mMaterialType, keyWord, orderColumn, orderType, brands, brandsString,
                    categoryAttrValueIds, expandAttrValueIds, minPrice, maxPrice, page, pageSize, isDialog);
        }
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onQueryItemListByKeywordSuccess(ArrayList<ItemAttribute> itemAttributes, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear) {
        mAdapter.update(itemAttributes, isClear);
        binding.smart.finishLoadMore();
        if (!filtered) mSearchFilterList = filterResults;
    }

    @Override
    public void onQueryItemListByKeywordSuccess2(String itemId, String skuId) {
        Bundle bundle = new Bundle();
        bundle.putString("itemId", itemId);
        bundle.putString("skuId", skuId);
        ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onQueryItemListByCidSuccess(ArrayList<ItemAttribute> results, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear) {
        mAdapter.update(results, isClear);
        binding.smart.finishLoadMore();
        if (!filtered) mSearchFilterList = filterResults;
    }

    @Override
    public void sort(int page, String orderColumn, String orderType, String keyword, String cid) {
        page = DEF_PAGE;
        this.orderColumn = orderColumn;
        this.orderType = orderType;
        this.mSearchKey = keyword;
        this.mCid = cid;
        this.page = page;
        notifyData(mSearchKey, mCid, orderColumn, orderType,
                brands, brandsString, categoryAttrValueIds, expandAttrValueIds,
                minPrice, maxPrice, page, 30, true);
    }

    @Override
    public void search(int page) {
        notifyData(mSearchKey, mCid, orderColumn, orderType,
                brands, brandsString, categoryAttrValueIds, expandAttrValueIds,
                minPrice, maxPrice, page, 30, true);
    }

    @Override
    public ArrayList<SearchFilterBean> getFilterData() {
        return mSearchFilterList;
    }

    @Override
    public void sendFilterData(String[] strings, int page) {
        filtered = true;
        mCid = strings[1];
        brands = brandsString = strings[0];
        categoryAttrValueIds = strings[2];
        expandAttrValueIds = strings[3];
        minPrice = strings[4];
        maxPrice = strings[5];
        this.page = page;
        notifyData(mSearchKey, mCid, orderColumn, orderType,
                brands, brandsString, categoryAttrValueIds, expandAttrValueIds,
                minPrice, maxPrice,
                page, 30, true);
    }
}
