package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.NoticeBean;
import com.rails.lib_data.model.CategoryModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.NetWorkUtil;
import com.rails.purchaseplatform.framwork.utils.file.FileCacheUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 商城分类实现类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/3
 */
public class CategoryPresenterImpl extends BasePresenter<CategoryContract.CategoryView> implements CategoryContract.CategoryPresenter {

    private CategoryModel model;
    //缓存分类数据
    private final String fileName = "categoryInfo";

    public CategoryPresenterImpl(Activity mContext, CategoryContract.CategoryView categoryView) {
        super(mContext, categoryView);
        model = new CategoryModel();
    }


    /**
     * @param isReadCache 是否读取缓存，暂时不用
     */
    @Override
    public void getCategorys(boolean isReadCache, boolean isDialog) {

        if (isReadCache) {
            ArrayList<CategoryRootBean> beans = (ArrayList<CategoryRootBean>) FileCacheUtil.getInstance(mContext).readObject(fileName);
            if (beans != null)
                baseView.getCategorys(beans);
        }
        getCategory(isDialog);
    }


    private void getCategory(boolean isDialog) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);
        model.getCategorys(new HttpRxObserver<ArrayList<CategoryRootBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<CategoryRootBean> beans) {
                if (isCallBack()) {
                    baseView.dismissDialog();
                    baseView.getCategorys(beans);
                }
            }
        });
    }
}
