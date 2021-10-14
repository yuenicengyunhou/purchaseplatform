package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
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
    private int materialType;

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
                    materialType = response.getMaterialType();
                    baseView.loadShopInfo(response);
                } else {
                    ToastUtil.showCenter(mContext,"店铺信息为空");
                }

            }
        });
    }

    /**
     * keyword  搜索关键字
     */
    @Override
    public void getShopItemList(boolean showLoading, String shopInfoId, int page, int pageSize, String orderColumn, String orderType, ArrayList<SearchFilterBean> list, String keyword) {
        if (null == shopInfoId) {
            ToastUtil.showCenter(mContext, "店铺id为空");
            return;
        }

        if (showLoading) {
            baseView.showResDialog(R.string.loading);
        }

        model.getShopItemList(shopInfoId, page, pageSize, orderColumn, orderType, list, keyword, materialType, new HttpRxObserver<ShopRecommendBean>() {
            @Override
            protected void onError(ErrorBean e) {
                if (showLoading) {
                    baseView.dismissDialog();
                }

                baseView.onError(e);
                ArrayList<ResultListBean> listBean = new ArrayList<>();
                baseView.loadShopProductList(listBean,0);
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
