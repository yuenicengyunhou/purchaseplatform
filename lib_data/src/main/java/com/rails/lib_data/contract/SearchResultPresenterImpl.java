package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.SearchDataBean;
import com.rails.lib_data.bean.SearchItemBean;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.bean.SkuItemBean;
import com.rails.lib_data.model.ProductModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchResultPresenterImpl extends BasePresenter<SearchResultContract.SearchResultView> implements SearchResultContract.SearchResultPresenter {
    final private String TAG = SearchResultPresenterImpl.class.getSimpleName();

    private ProductModel model;

    public SearchResultPresenterImpl(Activity mContext, SearchResultContract.SearchResultView searchResultView) {
        super(mContext, searchResultView);
        model = new ProductModel();
    }

    @Override
    public void getSearchResult(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        Type type = new TypeToken<ArrayList<SearchResultBean>>() {
        }.getType();
        ArrayList<SearchResultBean> beans = JsonUtil.parseJson(mContext, "searchResult.json", type);

//        model.getProducts();

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getSearchResult(beans, false, isClear);
        }

    }

    @Override
    public void getSearchResultWithKeywordOnly(boolean isDialog, int pageNum, long platformId, String keyword) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.getSearchResultWithKeywordOnly(pageNum, platformId, keyword, new HttpRxObserver<SearchDataBean>() {
            @Override
            protected void onError(ErrorBean e) {
                Log.d(TAG, " ======= " + " == Error == " + e.getMsg());
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(SearchDataBean response) {
//                baseView.onResult(response);
                Log.d(TAG, " ======= " + " == Success == " + response.getTotalCount());

                for (SearchItemBean searchItemBean : response.getItemList().getResultList()) {
                    SkuItemBean sku = searchItemBean.getItem_sku().get(0);
                    sku.getSkuName();
                    // TODO: 2021/04/16 处理数据
                }

                baseView.dismissDialog();

                if (isCallBack()) {
                    baseView.dismissDialog();
                    boolean isClear = pageNum <= 1;
//                    baseView.getProducts(response, true, isClear);
                }
            }
        });
    }
}
