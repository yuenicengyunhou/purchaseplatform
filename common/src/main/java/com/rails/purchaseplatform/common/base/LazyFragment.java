package com.rails.purchaseplatform.common.base;

import android.os.Bundle;

import androidx.viewbinding.ViewBinding;

/**
 * fragment懒加载
 * author :Created by wangchao
 * date : 2017/5/15.
 */

public abstract class LazyFragment<T extends ViewBinding> extends BaseErrorFragment<T> {

    //是否第一次加载
    protected boolean isFristLoad = true;


    @Override
    protected void initialize(Bundle bundle) {
//        lazyLoad();
    }


    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    /**
     *
     */
    protected void lazyLoad() {
        if (isFristLoad) {
            loadData();
            isFristLoad = false;
        }

        loadPreVisitData();
    }


    /**
     * 首次进入加载数据
     */
    protected abstract void loadData();


    /**
     * 每日此进入都加载数据
     */
    protected abstract void loadPreVisitData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFristLoad = true;
    }

}
