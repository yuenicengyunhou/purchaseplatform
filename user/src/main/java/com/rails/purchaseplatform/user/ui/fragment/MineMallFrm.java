package com.rails.purchaseplatform.user.ui.fragment;

import android.view.View;

import com.rails.purchaseplatform.common.ConRoute;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.user.databinding.FrmMineMallBinding;

/**
 * 购物车--个人中心
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MineMallFrm extends LazyFragment<FrmMineMallBinding> {
    @Override
    protected void loadData() {
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    protected void onClick(){
        super.onClick();
        binding.myChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ORDER.ORDER_MAIN).navigation();
            }
        });
    }
}
