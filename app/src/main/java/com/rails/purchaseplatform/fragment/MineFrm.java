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
