package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.model.SearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

public class SearchShopPresenterImpl extends BasePresenter<SearchContract.SearchShopView>
        implements
        SearchContract.SearchShopPresenter {


    final private String TAG = SearchItemPresenterImpl.class.getSimpleName();

    private SearchModel model;

    public SearchShopPresenterImpl(Activity mContext, SearchContract.SearchShopView searchShopView) {
        super(mContext, searchShopView);
        model = new SearchModel();
    }


    @Override
    public void getShopListWithKeywordOnly(long platformId, long accountId, String keyword, boolean isBuy, int pageNum, int pageSize, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        model.getShopListWithKeywordOnly(platformId, accountId, keyword, isBuy, pageNum, pageSize, new HttpRxObserver() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Object response) {
                baseView.dismissDialog();
                // TODO: 2021/04/19 处理数据并返回
//                baseView.getSearchResultWithKeywordOnly();
                baseView.getShopListWithKeywordOnly(new ArrayList<String>(), true, true);
            }
        });
    }
}
