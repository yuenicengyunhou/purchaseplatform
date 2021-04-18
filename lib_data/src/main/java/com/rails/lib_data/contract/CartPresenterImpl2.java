package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.model.CartModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * 商品详情页 请求添加到购物车
 */
public class CartPresenterImpl2 extends BasePresenter<CartContract.DetailsCartView> implements CartContract.CartPresenter2 {

    private CartModel mModel;

    public CartPresenterImpl2(Activity mContext, CartContract.DetailsCartView cartView) {
        super(mContext, cartView);
        mModel = new CartModel();
    }

    @Override
    public void addCart(long platformId, long organizeId, long accountId, int accountType, String skuSaleNumJson, boolean isDialog) {

//        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.addCart(platformId, organizeId, accountId, accountType, skuSaleNumJson,
                new HttpRxObserver() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.dismissDialog();
                        baseView.onError(e);
                    }

                    @Override
                    protected void onSuccess(Object response) {
                        baseView.dismissDialog();
                        baseView.addCartSuccess();
                    }
                });
    }
}
