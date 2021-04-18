package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.SearchDataBean;
import com.rails.lib_data.bean.SearchItemBean;
import com.rails.lib_data.bean.SkuItemBean;
import com.rails.lib_data.bean.showOnApp.BaseItemAttribute;
import com.rails.lib_data.model.ProductModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

public class SearchResultPresenterImpl extends BasePresenter<SearchResultContract.SearchResultView> implements SearchResultContract.SearchResultPresenter {
    final private String TAG = SearchResultPresenterImpl.class.getSimpleName();

    private ProductModel model;

    public SearchResultPresenterImpl(Activity mContext, SearchResultContract.SearchResultView searchResultView) {
        super(mContext, searchResultView);
        model = new ProductModel();
    }

    @Override
    public void getSearchResultWithKeywordOnly(boolean isDialog, int pageNum, long platformId, String keyword) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.getSearchResultWithKeywordOnly(pageNum, platformId, keyword, new HttpRxObserver<SearchDataBean>() {
            @Override
            protected void onError(ErrorBean e) {
                Log.d(TAG, " ======= " + " == Error == " + e.getMsg());
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(SearchDataBean response) {
                ArrayList<BaseItemAttribute> baseItemAttributes = new ArrayList<>();
                for (SearchItemBean searchItemBean : response.getItemList().getResultList()) {
                    BaseItemAttribute attribute = new BaseItemAttribute();
                    SkuItemBean sku = searchItemBean.getItem_sku().get(0);
                    attribute.setCid(sku.getCid());
                    attribute.setSkuName(sku.getSkuName());
                    attribute.setPictureUrl(sku.getPictureUrl());
                    attribute.setShopId(sku.getShopId());
                    attribute.setShopName(searchItemBean.getShopName());
                    attribute.setSellPrice(sku.getSellPrice());
                    baseItemAttributes.add(attribute);
                }

                baseView.dismissDialog();

                if (isCallBack()) {
                    baseView.dismissDialog();
                    boolean isClear = pageNum <= 1;
                    baseView.getSearchResultWithKeywordOnly(baseItemAttributes, true, isClear);
                }
            }
        });
    }
}
