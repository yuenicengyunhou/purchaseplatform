package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.lib_data.model.HotSearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

public class HotSearchPresenterImpl extends BasePresenter<HotSearchContract.HotSearchView> implements HotSearchContract.HotSearchPresenter {

    private HotSearchModel model;

    public HotSearchPresenterImpl(Activity mContext, HotSearchContract.HotSearchView hotSearchView) {
        super(mContext, hotSearchView);
        model = new HotSearchModel();
    }

    @Override
    public void getHotSearch(boolean isDialog, int page) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        model.getHotSearch(new HttpRxObserver<ArrayList<HotSearchBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ArrayList<HotSearchBean> response) {
                boolean isClear = page <= 1;
                baseView.getHotSearch(response, false, isClear);
                baseView.dismissDialog();
            }
        });
    }
}
