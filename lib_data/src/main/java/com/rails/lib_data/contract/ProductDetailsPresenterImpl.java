package com.rails.lib_data.contract;

import android.app.Activity;
import android.widget.Toast;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemResult;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSku;
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

    /**
     * Constructor
     *
     * @param mContext
     * @param productDetailsView
     */
    public ProductDetailsPresenterImpl(
            Activity mContext,
            ProductDetailsContract.ProductDetailsView productDetailsView) {
        super(mContext, productDetailsView);
        mModel = new ProductDetailsModel();
    }

    /**
     * 获取商品详情
     *
     * @param platformId
     * @param itemId
     * @param companyId
     * @param isDialog
     */
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
                baseView.onGetProductDetailsSuccess(response);
                baseView.dismissDialog();
            }
        });

    }

    /**
     * 获取商品价格、评分
     *
     * @param platformId
     * @param skuId
     * @param isDialog
     */
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

    /**
     * 获取店铺推荐
     *
     * @param platformId
     * @param keyword
     * @param cid
     * @param shopId
     * @param isDialog
     */
    @Override
    public void getHotSale(long platformId, String keyword, int cid, long shopId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getHotSale(platformId, keyword, cid, shopId, new HttpRxObserver<HotSaleBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(HotSaleBean response) {
                ArrayList<RecommendItemsBean> beans = new ArrayList<>();
                for (ItemResult element : response.getItemList().getResultList()) {
                    RecommendItemsBean bean = new RecommendItemsBean();
                    ItemSku sku = element.getItem_sku().get(0);
                    bean.setName(sku.getSkuName());
                    bean.setImageUrl(sku.getPictureUrl());
                    bean.setPrice(String.valueOf(sku.getSellPrice()));
                    beans.add(bean);
                }

                baseView.onGetHotSaleSuccess(beans);
                baseView.dismissDialog();
            }
        });
    }
}
