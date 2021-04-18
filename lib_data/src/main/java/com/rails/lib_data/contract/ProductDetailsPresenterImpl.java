package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.ProductDetailsBean;
import com.rails.lib_data.model.ProductDetailsModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * 商品详情 presenter实现类
 */
public class ProductDetailsPresenterImpl
        extends BasePresenter<ProductDetailsContract.ProductDetailsView>
        implements ProductDetailsContract.ProductDetailsPresenter {

    private ProductDetailsModel mModel;

    public ProductDetailsPresenterImpl(
            Activity mContext,
            ProductDetailsContract.ProductDetailsView productDetailsView) {
        super(mContext, productDetailsView);
        mModel = new ProductDetailsModel();
    }

    @Override
    public void getProductDetails(long platformId, long shopId, long itemId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetails(platformId, shopId, itemId, new HttpRxObserver<ProductDetailsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ProductDetailsBean response) {
                baseView.dismissDialog();
                // TODO: 2021/04/18 参数
                baseView.onGetProductDetailsSuccess(true);
            }
        });

    }
}
