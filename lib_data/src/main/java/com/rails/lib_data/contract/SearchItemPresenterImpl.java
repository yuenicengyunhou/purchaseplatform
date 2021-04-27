package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forNetRequest.searchResult.SearchDataByItemBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.SearchItemBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.SkuItemBean;
import com.rails.lib_data.model.SearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

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
                Log.d(TAG, " ======= " + " == Error == " + e.getMsg());
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(SearchDataByItemBean response) {
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

                baseView.dismissDialog();

                if (isCallBack()) {
                    baseView.dismissDialog();
                    boolean isClear = pageNum <= 1;
                    baseView.getItemListWithKeywordOnly(itemAttributes, true, isClear);
                }
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

                baseView.getItemListWithCid(itemAttributes, false, true);
                baseView.dismissDialog();
            }
        });
    }

}
