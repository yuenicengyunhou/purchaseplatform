package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep1Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep2Bean;
import com.rails.lib_data.model.ProductDetailsModel2;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * 对{@link ProductDetailsPresenterImpl}类整合,实现并行和串行网络请求
 */
public class ProductDetailsPresenterImpl2
        extends BasePresenter<ProductDetailsContract2.ProductDetailsView2>
        implements ProductDetailsContract2.ProductDetailsPresenter2 {

    private ProductDetailsModel2 mModel;


    public ProductDetailsPresenterImpl2(Activity mContext, ProductDetailsContract2.ProductDetailsView2 productDetailsView2) {
        super(mContext, productDetailsView2);
        mModel = new ProductDetailsModel2();
    }


    @Override
    public void getProductDetailsStep1(String platformId, String itemId, String addressType, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetailsStep1(platformId, itemId, addressType, new HttpRxObserver<ProductDetailsStep1Bean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ProductDetailsStep1Bean response1) {
                String platformId = "20";
                String shopId = response1.getProductDetailsBean().getItemPublishVo().getShopId();
                String cid = String.valueOf(response1.getProductDetailsBean().getItemPublishVo().getCid());
                String skuId = response1.getProductDetailsBean().getItemSkuInfoList().get(0).getId();
                String provinceId = response1.getAddressBeanList().get(0).getProvinceCode();
                String cityId = response1.getAddressBeanList().get(0).getCityCode();
                String countryId = response1.getAddressBeanList().get(0).getCountryCode();
                String address = response1.getAddressBeanList().get(0).getFullAddress();
                String skuNum = "1";

                // TODO: 2021/6/8 继续下一步请求
                mModel.getProductDetailsStep2(platformId, shopId, cid, skuId, provinceId, cityId, countryId, address, skuNum, new HttpRxObserver<ProductDetailsStep2Bean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.onError(e);
                        baseView.dismissDialog();
                    }

                    @Override
                    protected void onSuccess(ProductDetailsStep2Bean response2) {
                        // TODO: 2021/6/8 解析数据 response1 response2 全解析出来
                        baseView.onProductInfoLoadCompleted();
                        baseView.dismissDialog();
                    }
                });
            }
        });
    }
}
