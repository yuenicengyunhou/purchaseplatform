package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPublishVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep1Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep2Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep3Bean;
import com.rails.lib_data.model.ProductDetailsModel2;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

/**
 * 对{@link ProductDetailsPresenterImpl}类整合,实现并行和串行网络请求
 */
public class ProductDetailsPresenterImpl2
        extends BasePresenter<ProductDetailsContract2.ProductDetailsView2>
        implements ProductDetailsContract2.ProductDetailsPresenter2 {

    final private String TAG = ProductDetailsPresenterImpl2.class.getSimpleName();

    private ProductDetailsModel2 mModel;

    public ProductDetailsPresenterImpl2(Activity mContext, ProductDetailsContract2.ProductDetailsView2 productDetailsView2) {
        super(mContext, productDetailsView2);
        mModel = new ProductDetailsModel2();
    }


    @Override
    public void getAllProductInfo(String platformId,
                                  String itemId,
                                  String paramSkuId,
                                  String addressType,
                                  boolean isDialog) {

        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetailsStep1(platformId, itemId, addressType, new HttpRxObserver<ProductDetailsStep1Bean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ProductDetailsStep1Bean response1) {
                // 数据工具类
                ProductDetailsDataUtils dataUtils = ProductDetailsDataUtils.getInstance();

                // 查询不到skuId 就终止下一步请求
                if ((response1.getProductDetailsBean() == null
                        || response1.getProductDetailsBean().getItemSkuInfoList() == null
                        || response1.getProductDetailsBean().getItemSkuInfoList().size() == 0)
                        && TextUtils.isEmpty(paramSkuId)) {
                    baseView.onHaveNoSkuId();
                    baseView.dismissDialog();
                    return;
                }

                // 下一步请求需要的参数 地址给默认值
                String platformId = "20";
                ItemPublishVo itemPublishVo = response1.getProductDetailsBean().getItemPublishVo();
                String shopId = itemPublishVo.getShopId();
                String cid = String.valueOf(itemPublishVo.getCid());
//                String skuId = TextUtils.isEmpty(paramSkuId)
//                        ? response1.getProductDetailsBean().getItemSkuInfoList().get(0).getId()
//                        : paramSkuId;
                // 先把作为参数传过来的paramSkuId赋值给skuId
                String skuId = paramSkuId;
                // 判断如果它是空的，就把ProductDetailsBean中skuId的取出来
                if (TextUtils.isEmpty(paramSkuId)) {
                    skuId = response1.getProductDetailsBean().getItemSkuInfoList().get(0).getId();
                }
                // 如果它不是空的，判断作为参数传过来的paramSkuId是否和ProductDetailsBean中skuId一致，如果不一致，使用ProductDetailsBean中skuId
                else if (!paramSkuId.equals(response1.getProductDetailsBean().getItemSkuInfoList().get(0).getId())) {
                    skuId = response1.getProductDetailsBean().getItemSkuInfoList().get(0).getId();
                }
                ArrayList<AddressBean> addressBeanList = response1.getAddressBeanList();
                String provinceId = dataUtils.getProvinceCode(addressBeanList);
                String cityId = dataUtils.getCityCode(addressBeanList);
                String countryId = dataUtils.getCountryCode(addressBeanList);
                String address = dataUtils.getFullAddress(addressBeanList);
                String skuNum = "1";
                String materialType = itemPublishVo.getMaterialType();
                if (materialType == null) materialType = "0";

                // 下一步请求
                String finalSkuId = skuId;
                mModel.getProductDetailsStep2(materialType, platformId, shopId, cid, skuId, provinceId, cityId, countryId, address, skuNum, new HttpRxObserver<ProductDetailsStep2Bean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.onError(e);
                        baseView.dismissDialog();
                    }

                    @Override
                    protected void onSuccess(ProductDetailsStep2Bean response2) {
                        if (isCallBack()) {
                            dataUtils.analysisAllData(mContext.getApplicationContext(), finalSkuId, response1, response2);
                            baseView.onProductInfoLoadCompleted(dataUtils.getProductDetailsPageBean());
                        }
                        baseView.dismissDialog();
                    }
                });
            }
        });
    }


    @Override
    public void getProductSkuInfo(String platformId,
                                  String cid,
                                  String skuId,
                                  String shopId,
                                  String provinceId,
                                  String cityId,
                                  String countryId,
                                  String address,
                                  String skuNum,
                                  boolean isDialog) {

        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetailsPop(platformId, cid, skuId, shopId, provinceId, cityId, countryId, address, skuNum, new HttpRxObserver<ProductDetailsStep3Bean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ProductDetailsStep3Bean response) {
                // 数据工具类
                ProductDetailsDataUtils dataUtils = ProductDetailsDataUtils.getInstance();
                dataUtils.analysisPopData(mContext.getApplicationContext(), response);
                baseView.onPopViewClick(dataUtils.getProductDetailsPopBean());
                baseView.dismissDialog();
            }
        });
    }
}
