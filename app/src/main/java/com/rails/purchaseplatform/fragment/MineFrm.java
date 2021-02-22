package com.rails.purchaseplatform.fragment;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmMineBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;

/**
 * 个人中心
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/26
 */
public class MineFrm extends LazyFragment<FrmMineBinding> {
    @Override
    protected void loadData() {

    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
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
