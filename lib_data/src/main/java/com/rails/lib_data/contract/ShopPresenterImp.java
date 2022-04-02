package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.ShopRateBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.shop.ItemListBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.lib_data.model.ShopModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;

public class ShopPresenterImp extends BasePresenter<ShopContract.ShopView> implements ShopContract.ShopPresenter {

    private final ShopModel model;
    private String keywordCache = "";
    //    private String platformId = null;
//    private int materialType;

    public ShopPresenterImp(Activity mContext, ShopContract.ShopView shopView) {
        super(mContext, shopView);
        model = new ShopModel();
//        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
//        if (null == bean) {
//            return;
//        }
//        platformId = bean.getPlatformId();

    }

    @Override
    public void getShopRating(String shopId) {
        if (null == shopId) {
            ToastUtil.showCenter(mContext, "店铺id为空");
            return;
        }
        model.getShopRating(shopId, new HttpRxObserver<ArrayList<ShopInfoBean>>() {
            @Override
            protected void onError(ErrorBean e) {
            }

            @Override
            protected void onSuccess(ArrayList<ShopInfoBean> response) {
                if ((null != response)&&(!response.isEmpty())) {
                    ShopInfoBean bean = response.get(0);
                    String rate = bean.getRate();
                    String shopRateSquence = getShopRate(rate);
                    baseView.loadShopRating(rate,shopRateSquence);
                }
            }
        });
    }

    public String getShopRate(String rate) {
        if (null == rate) {
            rate = "";
        }
        if (rate.contains("A") || rate.contains("B")) {
            return "风险较低";
        } else if (rate.contains("D") ) {
            return "风险极高";
        } else if (rate.contains("C")) {
            return "风险较高";
        }
        return "";
    }

    @Override
    public void getShopDetails(String id) {
        if (null == id) {
            ToastUtil.showCenter(mContext, "店铺id为空");
            return;
        }
        baseView.showResDialog(R.string.loading);
        model.getShopInfo(id, new HttpRxObserver<ShopInfoBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ShopInfoBean response) {
                baseView.dismissDialog();
                if (null != response) {
//            materialType = shop.getMaterialType();
//                    materialType = response.getMaterialType();
                    String shopId = response.getShopId();
                    if (null != shopId) {
                        getShopRating(shopId);
                    }
                    baseView.loadShopInfo(response);
                } else {
                    ToastUtil.showCenter(mContext, "店铺信息为空");
                }

            }
        });
    }



    /**
     * keyword  搜索关键字
     */
    @Override
    public void getShopItemList(boolean showLoading, String shopInfoId, int page, int pageSize, String orderColumn, String orderType, ArrayList<SearchFilterBean> list, String keyword, String lowPrice, String highPrice) {
        if (null == shopInfoId) {
            ToastUtil.showCenter(mContext, "店铺id为空");
            return;
        }

        if (showLoading) {
            baseView.showResDialog(R.string.loading);
        }

//        model.getShopItemList(shopInfoId, page, pageSize, orderColumn, orderType, list, keyword, materialType, new HttpRxObserver<ShopRecommendBean>() {
        model.getShopItemList(shopInfoId, page, pageSize, orderColumn, orderType, list, keyword, lowPrice, highPrice, new HttpRxObserver<ShopRecommendBean>() {
            @Override
            protected void onError(ErrorBean e) {
                if (showLoading) {
                    baseView.dismissDialog();
                }

                baseView.onError(e);
                ArrayList<ResultListBean> listBean = new ArrayList<>();
                baseView.loadShopProductList(listBean, 0);
            }

            @Override
            protected void onSuccess(ShopRecommendBean response) {
                if (showLoading) {
                    baseView.dismissDialog();
                }
                ItemListBean itemList = response.getItemList();
                ArrayList<ResultListBean> resultList = itemList.getResultList();
                int count = itemList.getCount();
                baseView.loadShopProductList(resultList, count);
                if (page < 2) {
                    ArrayList<SearchFilterBean> filterBeans = model.getFilterBeans(response);
                    baseView.loadFilter(filterBeans, !keywordCache.equals(keyword));
                    keywordCache = keyword;
                }
            }
        });
    }


}
