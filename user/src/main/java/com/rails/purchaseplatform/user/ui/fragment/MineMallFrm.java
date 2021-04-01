package com.rails.purchaseplatform.user.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.rails.purchaseplatform.common.ConRoute;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.user.BuildConfig;
import com.rails.purchaseplatform.user.databinding.FrmMineMallBinding;
import com.rails.purchaseplatform.user.ui.activity.BrowseActivity;

/**
 * 购物车--个人中心
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MineMallFrm extends LazyFragment<FrmMineMallBinding> {
    @Override
    protected void loadData() {
        onClick();

        binding.tabOrder.setNumber(3);
        binding.tabReceive.setNumber(20);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    protected void onClick() {
        super.onClick();
        binding.myChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", ConRoute.WEB.MODIFYPAW);
                startIntent(BrowseActivity.class, bundle);
            }
        });

        binding.orderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ORDER.ORDER_MAIN).navigation();
            }
        });

        binding.browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", ConRoute.WEB.ORDER_DETAIL);
                startIntent(BrowseActivity.class, bundle);
            }
        });


        binding.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", ConRoute.WEB.COLLECT);
                startIntent(BrowseActivity.class, bundle);
            }
        });


        binding.orderMeassge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.MSG.MSG_MAIN).navigation();
            }
        });


        binding.myAddressAdministration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAIN).navigation();
            }
        });

    }
}
