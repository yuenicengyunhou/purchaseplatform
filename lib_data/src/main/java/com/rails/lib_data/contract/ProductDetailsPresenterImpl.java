package com.rails.lib_data.contract;

import android.app.Activity;
import android.widget.Toast;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.model.ProductDetailsModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

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
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ProductDetailsBean response) {
                Toast.makeText(mContext, "12345 - 商品详情", Toast.LENGTH_LONG).show();


                baseView.onGetProductDetailsSuccess(response);
                baseView.dismissDialog();
            }
        });

    }

    @Override
    public void getProductPrice(long platformId, int skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductPrice(platformId, skuId, new HttpRxObserver<ArrayList<ProductPriceBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ArrayList<ProductPriceBean> response) {
                baseView.onGetProductPriceSuccess(response.get(0));
                baseView.dismissDialog();
            }
        });
    }

    @Override
    public void getHotSale(long platformId, long itemId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getHotSale(platformId, itemId, new HttpRxObserver<ArrayList<HotSaleBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ArrayList<HotSaleBean> response) {
                ArrayList<RecommendItemsBean> beans = new ArrayList<>();
                for (HotSaleBean element : response) {
                    RecommendItemsBean bean = new RecommendItemsBean();
                    bean.setName(element.getItemName());
                    bean.setImageUrl(element.getPictureUrl());
                    bean.setPrice(String.valueOf(element.getMaxPrice()));
                    beans.add(bean);
                }

                baseView.onGetHotSaleSuccess(beans);
                baseView.dismissDialog();
            }
        });
    }
}
