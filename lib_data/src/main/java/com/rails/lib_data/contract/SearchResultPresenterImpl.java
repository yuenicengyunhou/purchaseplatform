package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.model.SearchResultModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchResultPresenterImpl extends BasePresenter<SearchResultContract.SearchResultView> implements SearchResultContract.SearchResultPresenter {

    private SearchResultModel model;

    public SearchResultPresenterImpl(Activity mContext, SearchResultContract.SearchResultView searchResultView) {
        super(mContext, searchResultView);
        model = new SearchResultModel();
    }

    @Override
    public void getSearchResult(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        Type type = new TypeToken<ArrayList<SearchResultBean>>() {
        }.getType();

        ArrayList<SearchResultBean> beans = JsonUtil.parseJson(mContext, "searchResult.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getSearchResult(beans, false, isClear);
        }

    }
}
