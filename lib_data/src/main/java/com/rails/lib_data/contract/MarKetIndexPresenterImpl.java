package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
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
    private final String fileName = "mallInfo.json";

    public MarKetIndexPresenterImpl(Activity mContext, MarketIndexContract.MarketIndexView marketIndexView) {
        super(mContext, marketIndexView);
        model = new MarketIndexModel();
    }

    @Override
    public void getRectProducts(boolean isDialog, boolean isHot) {
        if (isDialog)
            baseView.showDialog("加载中...");
        model.getRecProducts(new HttpRxObserver<ArrayList<ProductRecBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<ProductRecBean> beans) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getRecProducts(beans);
            }
        });
    }

    @Deprecated
    @Override
    public void getBanners() {
        Type type = new TypeToken<ArrayList<BannerBean>>() {
        }.getType();
        ArrayList<BannerBean> beans = JsonUtil.parseJson(mContext, "banner.json", type);
        baseView.getBanners(beans);
    }


    @Deprecated
    @Override
    public void getBrands() {
        Type type = new TypeToken<ArrayList<BrandBean>>() {
        }.getType();
        ArrayList<BrandBean> beans = JsonUtil.parseJson(mContext, "brand.json", type);
        baseView.getBrands(beans);
    }

    @Deprecated
    @Override
    public void getRecCategorys() {
        ArrayList<CategorySubBean> beans = new ArrayList<>();
        beans.add(new CategorySubBean("电子产品", R.drawable.ic_category_electronic));
        beans.add(new CategorySubBean("办公用品", R.drawable.ic_category_office));
        beans.add(new CategorySubBean("粮油食品", R.drawable.ic_category_food));
        beans.add(new CategorySubBean("通用工具", R.drawable.ic_category_tool));
        beans.add(new CategorySubBean("防疫物资", R.drawable.ic_category_goods));

        baseView.getRecCategorys(beans);
    }

    @Override
    public void getMarketIndexInfo(boolean isDialog) {
        if (!NetWorkUtil.checkNet(mContext)) {
            MarketIndexBean marketIndexBean = (MarketIndexBean) FileCacheUtil.getInstance(mContext).readObject(fileName);
            if (marketIndexBean != null)
                baseView.getIndexInfo(marketIndexBean);
            else {
                getIndexInfo(isDialog);
            }
        } else {
            getIndexInfo(isDialog);
        }


    }


    /**
     * 网络获取首页信息
     *
     * @param isDialog
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
