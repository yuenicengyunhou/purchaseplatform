package com.rails.lib_data.contract;

import android.app.Activity;
import android.widget.Toast;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
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

    private final String TAG = ProductDetailsPresenterImpl.class.getSimpleName();

    private ProductDetailsModel mModel;

    public ProductDetailsPresenterImpl(
            Activity mContext,
            ProductDetailsContract.ProductDetailsView productDetailsView) {
        super(mContext, productDetailsView);
        mModel = new ProductDetailsModel();
    }

    @Override
    public void getProductDetails(long platformId, long itemId, long companyId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetails(platformId, itemId, companyId, new HttpRxObserver<ProductDetailsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ProductDetailsBean response) {
                baseView.dismissDialog();
                Toast.makeText(mContext, "12334567989", Toast.LENGTH_LONG).show();
//                Log.d(TAG, "==========" + response);
                // TODO: 2021/04/18 参数
                baseView.onGetProductDetailsSuccess(true);
            }
        });

    }
}
