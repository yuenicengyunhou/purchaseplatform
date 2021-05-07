package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductBillBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemAfterSaleVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemResult;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSku;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.SupplierInfoImportData;
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
    public void getProductDetails(String platformId, String itemId, String companyId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetails(platformId, itemId, companyId, new HttpRxObserver<ProductDetailsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }


            @Override
            protected void onSuccess(ProductDetailsBean detailsBean) {

                ArrayList<ProductServiceBean> serviceBeans = new ArrayList<>();
                try {
                    ItemAfterSaleVo afterSale = detailsBean.getItemPublishVo().getItemAfterSaleVo();
                    serviceBeans.add(new ProductServiceBean(R.drawable.ic_category_all, "售后退货说明",
                            afterSale.getRefundService() == 1 ? "特殊商品不允许退货。" : "确认收货后" + afterSale.getRefundDuration() + "日内出现质量问题可申请退货。"));
                    serviceBeans.add(new ProductServiceBean(R.drawable.ic_category_all, "售后换货说明",
                            afterSale.getChangeService() == 1 ? "特殊商品，一经签收不予换货。。" : "确认收货后" + afterSale.getRefundDuration() + "日内出现质量问题可申请换货。"));
                    serviceBeans.add(new ProductServiceBean(R.drawable.ic_category_all, "售后质保说明",
                            "确认收货后" + afterSale.getRepaireDuration() + "月内出现质量问题可审请质保。"));
                } catch (Exception e) {
                }


                ArrayList<ProductServiceBean> companyBeans = new ArrayList<>();
                try {
                    SupplierInfoImportData suppData = detailsBean.getItemPublishVo().getSupplierInfoImportData();
                    String recommendOrg = TextUtils.isEmpty(suppData.getRecommendOrg()) ? "暂无数据" : suppData.getRecommendOrg();
                    String bindOrgName = TextUtils.isEmpty(suppData.getBindOrgName()) ? "暂无数据" : suppData.getBindOrgName();
                    String accountName = TextUtils.isEmpty(suppData.getAccountName()) ? "暂无数据" : suppData.getAccountName();
                    companyBeans.add(new ProductServiceBean(R.drawable.ic_category_all, "推荐单位", recommendOrg));
                    companyBeans.add(new ProductServiceBean(R.drawable.ic_category_all, "绑定货运单位", bindOrgName));
                    companyBeans.add(new ProductServiceBean(R.drawable.ic_category_all, "货运客户经理", accountName));
                } catch (Exception e) {

                }


                if (isCallBack()) {
                    baseView.onGetProductDetailsSuccess(detailsBean, serviceBeans, companyBeans);
                }

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
    public void getProductPrice(String platformId, String skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductPrice(platformId, skuId, new HttpRxObserver<ArrayList<ProductPriceBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ArrayList<ProductPriceBean> response) {
                baseView.dismissDialog();
                if (isCallBack()) {
                    ProductPriceBean bean = new ProductPriceBean();
                    ArrayList<ItemPicture> pics = new ArrayList<>();
                    ArrayList<ProductBillBean> billBeans = new ArrayList<>();
                    billBeans.add(new ProductBillBean("附件名称", "附件数量"));
                    if (response == null || response.isEmpty()) {
                        bean.setCreditLevel("0");
                        bean.setSellPrice(0.0D);
                        bean.setMarketPrice(0.0D);
                    } else {
                        bean = response.get(0);
                        billBeans.addAll(bean.getPackinglist());
                        pics.addAll(bean.getPictureUrl());
                    }
                    baseView.onGetProductPriceSuccess(bean, pics, billBeans);
                }
            }
        });
    }

    /**
     * 获取店铺推荐
     *
     * @param platformId
     * @param keyword
     * @param cid
     * @param pageNum
     * @param isDialog
     */
    @Override
    public void getHotSale(String platformId, String keyword, String cid, int pageNum, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getHotSale(platformId, keyword, cid, pageNum, new HttpRxObserver<HotSaleBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(HotSaleBean response) {
                ArrayList<RecommendItemsBean> beans = new ArrayList<>();
                for (ItemResult element : response.getItemList().getResultList()) {
                    for (ItemSku itemSku : element.getItem_sku()) {
                        RecommendItemsBean bean = new RecommendItemsBean();
                        bean.setName(itemSku.getSkuName());
                        bean.setImageUrl(itemSku.getPictureUrl());
                        bean.setPrice(String.valueOf(itemSku.getSellPrice()));
                        bean.setCid(itemSku.getCid());
                        bean.setShopId(itemSku.getShopId());
                        bean.setItemId(itemSku.getItemId());
                        bean.setSkuId(itemSku.getSkuId());
                        beans.add(bean);
                        if (beans.size() == 6) break;
                    }
                    if (beans.size() == 6) break;
                }

                baseView.onGetHotSaleSuccess(beans);
                baseView.dismissDialog();
            }
        });
    }

    @Override
    public void getUserCollect(String skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.getUserCollect(skuId, new HttpRxObserver<JSONObject>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(JSONObject response) {
                String string = response.toString();
                boolean isCollect = false;
                if (string.contains(String.valueOf(skuId)) && string.contains("true"))
                    isCollect = true;
                baseView.onGetUserCollectSuccess(isCollect);
                baseView.dismissDialog();
            }
        });
    }

    @Override
    public void getCartCount(String platformId, String organizeId, String accountId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.getCartCount(platformId, organizeId, accountId, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(String response) {
                baseView.onGetCartCountSuccess(response);
                baseView.dismissDialog();
            }
        });
    }


    /**
     * 获取邮费
     *
     * @param shopId
     */
    @Override
    public void getProductDelivery(String shopId) {
        mModel.getProductDelivery(shopId, new HttpRxObserver<DeliveryBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(DeliveryBean bean) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getDelivery(bean);
            }
        });
    }
}
