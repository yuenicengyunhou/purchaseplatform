package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.lib_data.bean.forNetRequest.searchResult.CategoryAttr;
import com.rails.lib_data.bean.forNetRequest.searchResult.CategoryAttrValue;
import com.rails.lib_data.bean.forNetRequest.searchResult.CidBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.ExpandAttr;
import com.rails.lib_data.bean.forNetRequest.searchResult.ExpandAttrValue;
import com.rails.lib_data.bean.forNetRequest.searchResult.SearchDataByItemBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.SearchItemBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.SkuItemBean;
import com.rails.lib_data.model.SearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchItemPresenterImpl extends BasePresenter<SearchContract.SearchItemView>
        implements
        SearchContract.SearchItemPresenter {

    final private String TAG = SearchItemPresenterImpl.class.getSimpleName();

    private SearchModel model;

    public SearchItemPresenterImpl(Activity mContext, SearchContract.SearchItemView searchItemView) {
        super(mContext, searchItemView);
        model = new SearchModel();
    }

    @Override
    public void getItemListWithKeywordOnly(String orderColumn, String orderType, String keyword, int pageNum, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.getItemListWithKeywordOnly(orderColumn, orderType, keyword, pageNum, new HttpRxObserver<SearchDataByItemBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(SearchDataByItemBean response) {
                baseView.dismissDialog();
                ArrayList<ItemAttribute> itemAttributes = getItemAttributes(response);
                ArrayList<SearchFilterBean> searchFilterBeans = getSearchFilterBeans(response);
                boolean isClear = pageNum <= 1;
                baseView.getItemListWithKeywordOnly(itemAttributes, searchFilterBeans, true, isClear);
            }
        });
    }

    @Override
    public void getItemListWithCid(String orderColumn, String orderType, String cid, int pageNum, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.getItemListWithCid(orderColumn, orderType, cid, pageNum, new HttpRxObserver<SearchDataByItemBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(SearchDataByItemBean response) {
                baseView.dismissDialog();
                ArrayList<ItemAttribute> itemAttributes = getItemAttributes(response);
                ArrayList<SearchFilterBean> searchFilterBeans = getSearchFilterBeans(response);
                boolean isClear = pageNum <= 1;
                baseView.getItemListWithCid(itemAttributes, searchFilterBeans, false, isClear);
            }
        });
    }

    @Override
    public void queryItemListByCid(String keyword, String cid, String orderColumn, String orderType,
                                   String brands, String brandsString, String categoryAttrValueIds, String expandAttrValueIds,
                                   String minPrice, String maxPrice, int pageNum, int pageSize, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.queryItemListByCid(keyword, cid, orderColumn, orderType, brands, brandsString,
                categoryAttrValueIds, expandAttrValueIds, minPrice, maxPrice, pageNum, pageSize,
                new HttpRxObserver<SearchDataByItemBean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.onError(e);
                        baseView.dismissDialog();
                    }

                    @Override
                    protected void onSuccess(SearchDataByItemBean response) {
                        ArrayList<ItemAttribute> itemAttributes = getItemAttributes(response);
                        ArrayList<SearchFilterBean> searchFilterBeans = getSearchFilterBeans(response);
                        boolean isClear = pageNum <= 1;
                        baseView.getItemListWithCid(itemAttributes, searchFilterBeans, false, isClear);
                        baseView.dismissDialog();
                    }
                });
    }

    @Override
    public void queryItemListByKeyword(String keyword, String orderColumn, String orderType,
                                       String brands, String brandsString, String categoryAttrValueIds, String expandAttrValueIds,
                                       String minPrice, String maxPrice, int pageNum, int pageSize, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.queryItemListByKeyword(keyword, orderColumn, orderType,
                brands, brandsString, categoryAttrValueIds, expandAttrValueIds,
                minPrice, maxPrice, pageNum, pageSize,
                new HttpRxObserver<SearchDataByItemBean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.onError(e);
                        baseView.dismissDialog();
                    }

                    @Override
                    protected void onSuccess(SearchDataByItemBean response) {
                        ArrayList<ItemAttribute> itemAttributes = getItemAttributes(response);
                        ArrayList<SearchFilterBean> searchFilterBeans = getSearchFilterBeans(response);
                        boolean isClear = pageNum <= 1;
                        baseView.getItemListWithKeywordOnly(itemAttributes, searchFilterBeans, false, isClear);
                        baseView.dismissDialog();
                    }
                });
    }

    /**
     * 获取商品属性
     *
     * @param response response
     * @return 商品集合
     */
    @NotNull
    private ArrayList<ItemAttribute> getItemAttributes(SearchDataByItemBean response) {
        ArrayList<ItemAttribute> itemAttributes = new ArrayList<>();
        for (SearchItemBean searchItemBean : response.getItemList().getResultList()) {
            ItemAttribute attribute = new ItemAttribute();
            SkuItemBean sku = searchItemBean.getItem_sku().get(0);
            attribute.setCid(sku.getCid());
            attribute.setSkuName(sku.getSkuName());
            attribute.setPictureUrl(sku.getPictureUrl());
            attribute.setShopId(sku.getShopId());
            attribute.setShopName(searchItemBean.getShopName());
            attribute.setSellPrice(sku.getSellPrice());
            attribute.setItemId(sku.getItemId());
            attribute.setSkuId(sku.getSkuId());
            attribute.setCid(sku.getCid());
            attribute.setShopId(sku.getShopId());
            itemAttributes.add(attribute);
        }
        return itemAttributes;
    }

    /**
     * 获取筛选属性
     *
     * @param response response
     * @return 筛选属性集合
     */
    @NotNull
    private ArrayList<SearchFilterBean> getSearchFilterBeans(SearchDataByItemBean response) {
        ArrayList<SearchFilterBean> searchFilterBeans = new ArrayList<>(); // 最终要传递到View层展示
        ArrayList<String> brands = (ArrayList<String>) response.getBrands();
        if (brands != null && brands.size() != 0) {
            SearchFilterBean searchFilterBean = new SearchFilterBean();
            searchFilterBean.setFilterName("品牌");
            ArrayList<SearchFilterValue> searchFilterValues = new ArrayList<>();
            for (String brand : brands) {
                SearchFilterValue searchFilterValue = new SearchFilterValue();
                searchFilterValue.setValueName(brand);
                searchFilterValue.setAttrFlag(0);
                searchFilterValues.add(searchFilterValue);
            }
            searchFilterBean.setFilterValues(searchFilterValues);
            searchFilterBeans.add(searchFilterBean);
        }
        if (response.getAllCids() != null && response.getAllCids().size() != 0) {
            SearchFilterBean searchFilterBean = new SearchFilterBean();
            searchFilterBean.setFilterName("类目");
            searchFilterBean.setMultiSelect(false);
            ArrayList<SearchFilterValue> searchFilterValues = new ArrayList<>();
            for (CidBean cidBean : response.getAllCids()) {
                SearchFilterValue searchFilterValue = new SearchFilterValue();
                searchFilterValue.setValueName(cidBean.getName());
                searchFilterValue.setValueId(String.valueOf(cidBean.getId()));
                searchFilterValue.setAttrFlag(1);
                searchFilterValues.add(searchFilterValue);
            }
            searchFilterBean.setFilterValues(searchFilterValues);
            searchFilterBeans.add(searchFilterBean);
        }
        if (response.getCategoryAttrs() != null && response.getCategoryAttrs().size() != 0) {
            for (CategoryAttr attr : response.getCategoryAttrs()) {
                SearchFilterBean searchFilterBean = new SearchFilterBean();
                searchFilterBean.setFilterName(attr.getAttrName());

                ArrayList<SearchFilterValue> searchFilterValues = new ArrayList<>();
                if (attr.getAttrValues() != null && attr.getAttrValues().size() != 0) {
                    for (CategoryAttrValue value : attr.getAttrValues()) {
                        SearchFilterValue searchFilterValue = new SearchFilterValue();
                        searchFilterValue.setValueName(value.getName());
                        searchFilterValue.setAttrFlag(2);
                        searchFilterValues.add(searchFilterValue);
                    }
                    searchFilterBean.setFilterValues(searchFilterValues);
                }
                searchFilterBeans.add(searchFilterBean);
            }
        }
        if (response.getExpandAttrs() != null && response.getExpandAttrs().size() != 0) {
            for (ExpandAttr attr : response.getExpandAttrs()) {
                SearchFilterBean searchFilterBean = new SearchFilterBean();
                searchFilterBean.setFilterName(attr.getAttrName());

                ArrayList<SearchFilterValue> searchFilterValues = new ArrayList<>();
                if (attr.getAttrValues() != null && attr.getAttrValues().size() != 0) {
                    for (ExpandAttrValue value : attr.getAttrValues()) {
                        SearchFilterValue searchFilterValue = new SearchFilterValue();
                        searchFilterValue.setValueName(value.getName());
                        searchFilterValue.setAttrFlag(3);
                        searchFilterValues.add(searchFilterValue);
                    }
                    searchFilterBean.setFilterValues(searchFilterValues);
                }
                searchFilterBeans.add(searchFilterBean);
            }
        }
        return searchFilterBeans;
    }

}
