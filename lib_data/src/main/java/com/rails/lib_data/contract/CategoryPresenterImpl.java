package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.model.CategoryModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

/**
 * 商城分类实现类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/3
 */
public class CategoryPresenterImpl extends BasePresenter<CategoryContract.CategoryView> implements CategoryContract.CategoryPresenter {

    private CategoryModel model;

    public CategoryPresenterImpl(Activity mContext, CategoryContract.CategoryView categoryView) {
        super(mContext, categoryView);
        model = new CategoryModel();
    }


    /**
     * @param isReadCache 是否读取缓存，暂时不用
     */
    @Override
    public void getCategorys(boolean isReadCache) {
        model.getCategorys(new HttpRxObserver<ArrayList<CategoryRootBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<CategoryRootBean> beans) {
                if (isCallBack()) {
                    baseView.getCategorys(beans);
                }
            }
        });
    }
}
