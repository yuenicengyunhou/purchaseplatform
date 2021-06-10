package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.model.MarketIndexModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.NetWorkUtil;
import com.rails.purchaseplatform.framwork.utils.file.FileCacheUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 商城首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarKetIndexPresenterImpl extends BasePresenter<MarketIndexContract.MarketIndexView> implements MarketIndexContract.MarketIndexPresenter {

    private MarketIndexModel model;
    //缓存首页数据的文件
    private final String fileName = "mallInfo";

    public MarKetIndexPresenterImpl(Activity mContext, MarketIndexContract.MarketIndexView marketIndexView) {
        super(mContext, marketIndexView);
        model = new MarketIndexModel();
    }

    @Override
    public void getHotProducts(boolean isDialog, int page, String pageSize) {
        if (isDialog)
            baseView.showDialog("加载中...");
        model.getHotProducts(page, pageSize, new HttpRxObserver<ListBeen<ProductBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ListBeen<ProductBean> listBeen) {
                baseView.dismissDialog();
                if (isCallBack()) {
                    if (listBeen != null)
                        baseView.getHotProducts(listBeen.getList());
                }

            }
        });
    }


    @Override
    public void getMarketIndexInfo(boolean isCache, boolean isDialog) {
        if (isCache) {
            MarketIndexBean marketIndexBean = (MarketIndexBean) FileCacheUtil.getInstance(mContext).readObject(fileName);
            if (marketIndexBean != null) {
                baseView.getIndexInfo(marketIndexBean);
            }
        }
        getIndexInfo(isDialog);
    }

    @Override
    public void getRanks(boolean isDialog, int page, String pageSize, String categoryId) {
        if (TextUtils.isEmpty(categoryId)) {
            model.getRecBrands(page, pageSize, new HttpRxObserver<ListBeen<BrandBean>>() {
                @Override
                protected void onError(ErrorBean e) {
                    baseView.dismissDialog();
                    if (page == 1){
                        ArrayList<BrandBean> productBeans = new ArrayList<>();
                        baseView.getBrands(productBeans,false,true);
                    }else{
                        baseView.onError(e);
                    }
                }

                @Override
                protected void onSuccess(ListBeen<BrandBean> listBeen) {
                    baseView.dismissDialog();
                    if (isCallBack()){
                        boolean isClear = listBeen.getPageNum()== 1;
                        boolean hasMore = listBeen.getPageNum()< listBeen.getTotalPageCount();
                        baseView.getBrands(listBeen.getList(),hasMore,isClear);
                    }
                }
            });
        } else if ("1".equals(categoryId)) {
            model.getHotProducts(page, pageSize, new HttpRxObserver<ListBeen<ProductBean>>() {
                @Override
                protected void onError(ErrorBean e) {
                    baseView.dismissDialog();
                    if (page == 1){
                        ArrayList<ProductBean> productBeans = new ArrayList<>();
                        baseView.getFloorProducts(productBeans,false,true);
                    }else{
                        baseView.onError(e);
                    }
                }

                @Override
                protected void onSuccess(ListBeen<ProductBean> listBeen) {
                    baseView.dismissDialog();
                    if (isCallBack()){
                        boolean isClear = listBeen.getPageNum()== 1;
                        boolean hasMore = listBeen.getPageNum()< listBeen.getTotalPageCount();
                        baseView.getFloorProducts(listBeen.getList(),hasMore,isClear);
                    }
                }

            });
        } else {
            model.getFloorProducts(page, pageSize, categoryId, new HttpRxObserver<ListBeen<ProductBean>>() {

                @Override
                protected void onError(ErrorBean e) {
                    baseView.dismissDialog();
                    if (page == 1){
                        ArrayList<ProductBean> productBeans = new ArrayList<>();
                        baseView.getFloorProducts(productBeans,false,true);
                    }else{
                        baseView.onError(e);
                    }

                }

                @Override
                protected void onSuccess(ListBeen<ProductBean> listBeen) {
                    baseView.dismissDialog();
                    if (isCallBack()){
                        boolean isClear = listBeen.getPageNum()== 1;
                        boolean hasMore = listBeen.getPageNum()< listBeen.getTotalPageCount();
                        baseView.getFloorProducts(listBeen.getList(),hasMore,isClear);
                    }
                }
            });
        }
    }


    /**
     * 网络获取首页信息
     */
    private void getIndexInfo(boolean isDialog) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);
        model.getMarketIndexInfo(new HttpRxObserver<MarketIndexBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(MarketIndexBean response) {
                baseView.dismissDialog();
                baseView.getIndexInfo(response);
                FileCacheUtil.getInstance(mContext).writeObject(response, fileName);
            }


            @Override
            public void onComplete() {
                super.onComplete();
                baseView.dismissDialog();
            }
        });
    }
}
