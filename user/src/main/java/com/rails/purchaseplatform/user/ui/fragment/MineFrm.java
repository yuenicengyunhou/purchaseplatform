package com.rails.purchaseplatform.user.ui.fragment;

import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.FrmMineBinding;

/**
 * 平台--个人中心
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/26
 */
public class MineFrm extends LazyFragment<FrmMineBinding> {

    public MineFrm(){}

    @Override
    protected void loadData() {
        // TODO: 2021/2/25 类的初始化页面数据在这里处理 
    }

    @Override
    protected void loadPreVisitData() {
        // TODO: 2021/2/25 如果每次切换页面都要更新数据，就将loadData数据请求的方法，放在这里操作 
        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

}
