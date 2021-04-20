package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.model.ShopModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

public class ShopPresenterImp extends BasePresenter<ShopContract.ShopView> implements ShopContract.ShopPresenter {

    private final ShopModel model;

    public ShopPresenterImp(Activity mContext, ShopContract.ShopView shopView) {
        super(mContext, shopView);
        model = new ShopModel();
    }

    @Override
    public void getShopDetails(long id) {
        model.getShopInfo(20, id, new HttpRxObserver() {
            @Override
            protected void onError(ErrorBean e) {

            }

            @Override
            protected void onSuccess(Object response) {

            }
        });
    }
}
