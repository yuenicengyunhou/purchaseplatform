package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.AreaCodeBean;
import com.rails.lib_data.bean.AreaCodeFullBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.lib_data.contract.SearchContract;
import com.rails.lib_data.contract.SearchShopPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultByShopAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentSearchResultByShopBinding;
import com.rails.purchaseplatform.market.ui.activity.SearchResultActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * 搜索结果 - 按店铺搜索
 */
public class SearchResultByShopFragment extends LazyFragment<FragmentSearchResultByShopBinding>
        implements
        SearchContract.SearchShopView,
        SearchResultActivity.OnSortClick,
        SearchResultActivity.OnShopFilterClick {

    final private String TAG = SearchResultByShopFragment.class.getSimpleName();

    private SearchResultByShopAdapter mAdapter;
    private SearchContract.SearchShopPresenter mPresenter;

    private ArrayList<SearchFilterBean> mSearchFilterList;

    private String mSearchKey;
    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private String orderColumn;
    private String orderType;
    private String saleArea;
    private String shopType;
    private String isBought;

    @Override
    protected void loadData() {
        Bundle bundle = this.getArguments();
        mSearchKey = bundle.getString("search_key", "");

        mAdapter = new SearchResultByShopAdapter(this.getContext());
        binding.brvSearchResultByShopRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.brvSearchResultByShopRecycler.setAdapter(mAdapter);
        binding.brvSearchResultByShopRecycler.setEmptyView(binding.empty);

        mSearchFilterList = getFilter();

        mPresenter = new SearchShopPresenterImpl(this.getActivity(), this);
        onRefresh();
    }

    /**
     * 获取筛选条件
     */
    private ArrayList<SearchFilterBean> getFilter() {
        ArrayList<SearchFilterBean> searchFilterBeanList = new ArrayList<>();

        SearchFilterValue value01 = new SearchFilterValue();
        value01.setValueName("  是  ");
        value01.setValueId("true");
        SearchFilterValue value02 = new SearchFilterValue();
        value02.setValueName("  全部  ");
        value02.setValueId("false");
        value02.setSelect(true);
        ArrayList<SearchFilterValue> values0 = new ArrayList<>();
        values0.add(value01);
        values0.add(value02);
        SearchFilterBean bean0 = new SearchFilterBean();
        bean0.setFilterName("是否购买过");
        bean0.setFilterValues(values0);
        bean0.setMultiSelect(false);

        SearchFilterValue value1 = new SearchFilterValue();
        value1.setValueId("1");
        value1.setValueName("集货商");
        SearchFilterValue value2 = new SearchFilterValue();
        value2.setValueId("2");
        value2.setValueName("供应商");
        ArrayList<SearchFilterValue> values = new ArrayList<>();
        values.add(value1);
        values.add(value2);
        SearchFilterBean bean1 = new SearchFilterBean();
        bean1.setFilterName("店铺类型");
        bean1.setFilterValues(values);
        bean1.setMultiSelect(false);

        Type type = new TypeToken<AreaCodeFullBean>() {
        }.getType();
        AreaCodeFullBean bean = JsonUtil.parseJson(getActivity(), "area_code.json", type);
        SearchFilterBean bean2 = new SearchFilterBean();
        bean2.setFilterName("收货区域");
        bean2.setMultiSelect(false);
        ArrayList<SearchFilterValue> values2 = new ArrayList<>();
        for (AreaCodeBean codeBean : bean.getData()) {
            SearchFilterValue value = new SearchFilterValue();
            value.setValueId(codeBean.getCode());
            value.setValueName(codeBean.getName());
            values2.add(value);
        }
        bean2.setFilterValues(values2);

        searchFilterBeanList.add(bean0);
        searchFilterBeanList.add(bean1);
        searchFilterBeanList.add(bean2);

        return searchFilterBeanList;
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
            notifyData(isBought, page, mSearchKey, orderColumn, orderType, shopType, saleArea, false);
        });
        notifyData(isBought, page, mSearchKey, orderColumn, orderType, shopType, saleArea, true);
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
    void notifyData(String isBought, int page, String keyWord, String orderColumn, String orderType, String shopType, String saleArea, boolean isDialog) {
        mPresenter.getShopListWithKeywordOnly(isBought, page, 30, keyWord, orderColumn, orderType, shopType, saleArea, isDialog);
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
    public void sort(int page, String orderColumn, String orderType, String keyword, String cid) {
        this.orderColumn = orderColumn;
        this.orderType = orderType;
        this.mSearchKey = keyword;
        this.page = page;
        page = DEF_PAGE;
        notifyData(isBought, page, mSearchKey, orderColumn, orderType, shopType, saleArea, true);
    }

    @Override
    public void search(int page) {
        notifyData(isBought, page, mSearchKey, orderColumn, orderType, shopType, saleArea, true);
    }

    @Override
    public ArrayList<SearchFilterBean> getFilterData() {
        return mSearchFilterList;
    }

    @Override
    public void sendShopFilterData(int page, String isBought, String shopType, String saleArea) {
        this.isBought = isBought;
        this.shopType = shopType;
        this.saleArea = saleArea;
        this.page = page;
        notifyData(isBought, page, mSearchKey, orderColumn, orderType, this.shopType, this.saleArea, true);
    }
}
