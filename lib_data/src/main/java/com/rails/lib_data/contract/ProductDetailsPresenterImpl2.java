package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPageBean;
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
    public void getAllProductInfo(String platformId, String itemId, String addressType, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetailsStep1(platformId, itemId, addressType, new HttpRxObserver<ProductDetailsStep1Bean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ProductDetailsStep1Bean response1) {
                // 查询不到skuId 就终止下一步请求
                if (response1.getProductDetailsBean() == null
                        || response1.getProductDetailsBean().getItemSkuInfoList() == null
                        || response1.getProductDetailsBean().getItemSkuInfoList().size() == 0) {
                    baseView.onHaveNoSkuId();
                    baseView.dismissDialog();
                    return;
                }

                // 下一步请求需要的参数 地址给默认值
                String platformId = "20";
                String shopId = response1.getProductDetailsBean().getItemPublishVo().getShopId();
                String cid = String.valueOf(response1.getProductDetailsBean().getItemPublishVo().getCid());
                String skuId = response1.getProductDetailsBean().getItemSkuInfoList().get(0).getId();
                String provinceId = "11";
                String cityId = "1101";
                String countryId = "110101";
                String address = "北京市市辖区东城区";
                String skuNum = "1";

                // 用户有自己的地址 使用用户的地址替换
                if (response1.getAddressBeanList() != null
                        && response1.getAddressBeanList().size() != 0) {
                    AddressBean defaultAddress = response1.getAddressBeanList().get(0);
                    if (!TextUtils.isEmpty(defaultAddress.getProvinceCode()))
                        provinceId = response1.getAddressBeanList().get(0).getProvinceCode();
                    if (!TextUtils.isEmpty(defaultAddress.getCityCode()))
                        cityId = response1.getAddressBeanList().get(0).getCityCode();
                    if (!TextUtils.isEmpty(defaultAddress.getCountryCode()))
                        countryId = response1.getAddressBeanList().get(0).getCountryCode();
                    if (!TextUtils.isEmpty(defaultAddress.getFullAddress()))
                        address = response1.getAddressBeanList().get(0).getFullAddress();
                }

                // 下一步请求
                mModel.getProductDetailsStep2(platformId, shopId, cid, skuId, provinceId, cityId, countryId, address, skuNum, new HttpRxObserver<ProductDetailsStep2Bean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.onError(e);
                        baseView.dismissDialog();
                    }

                    @Override
                    protected void onSuccess(ProductDetailsStep2Bean response2) {
                        ProductDetailsPageBean productDetailsPageBean
                                = ProductDetailsDataUtils.getInstance()
                                .getBean(mContext.getApplicationContext(), response1, response2);

                        baseView.onProductInfoLoadCompleted(productDetailsPageBean);
                        baseView.dismissDialog();
                    }
                });
            }
        });
    }
}
