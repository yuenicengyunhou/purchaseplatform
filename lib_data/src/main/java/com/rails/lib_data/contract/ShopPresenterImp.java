package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.purchaseplatform.framwork.base.BasePresenter;

public class ShopPresenterImp extends BasePresenter<ShopContract.ShopView> implements ShopContract.ShopPresenter {
    public ShopPresenterImp(Activity mContext, ShopContract.ShopView shopView) {
        super(mContext, shopView);
    }

    @Override
    public void getShopDetails(long id) {

    }
}
