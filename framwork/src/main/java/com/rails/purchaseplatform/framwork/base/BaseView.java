package com.rails.purchaseplatform.framwork.base;


import com.rails.purchaseplatform.framwork.bean.ErrorBean;

/**
 * Created by wangchao on 2017/5/16.
 */

public interface BaseView {

    /**
     * data error
     *
     * @param errorBean
     */
    void onError(ErrorBean errorBean);

    /**
     * dialog loading
     */
    void showDialog(String msg);

    /**
     * 显示加载进度
     *
     * @param res
     */
    void showResDialog(int res);

    /**
     * dismiss  dialog loading
     */
    void dismissDialog();

}
