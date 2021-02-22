package com.rails.purchaseplatform.fragment;

import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmClassicalBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;

/**
 * 分类页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/26
 */
public class ClassicalFrm extends LazyFragment<FrmClassicalBinding> {
    @Override
    protected void loadData() {

    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarLightMode(getActivity());
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    public void onError(ErrorBean errorBean) {

    }

    @Override
    public void onFailure(String errorMsg) {

    }
}
