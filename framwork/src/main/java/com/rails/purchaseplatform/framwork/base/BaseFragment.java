package com.rails.purchaseplatform.framwork.base;

import android.app.Activity;
import android.content.Context;

/**
 * Created by wangchao on 2017/11/24.
 * 所有fragment的基类
 */

public abstract class BaseFragment extends BaseAbsFragment {

    public Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }
}
