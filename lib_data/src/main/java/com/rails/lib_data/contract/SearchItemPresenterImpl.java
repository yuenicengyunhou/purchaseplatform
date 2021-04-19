package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.SearchDataBean;
import com.rails.lib_data.bean.SearchItemBean;
import com.rails.lib_data.bean.SkuItemBean;
import com.rails.lib_data.bean.showOnApp.BaseItemAttribute;
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
    public void getItemListWithKeywordOnly(boolean isDialog, int pageNum, long platformId, String keyword) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.getItemListWithKeywordOnly(pageNum, platformId, keyword, new HttpRxObserver<SearchDataBean>() {
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
                    baseView.getItemListWithKeywordOnly(baseItemAttributes, true, isClear);
                }
            }
        });
    }

}
