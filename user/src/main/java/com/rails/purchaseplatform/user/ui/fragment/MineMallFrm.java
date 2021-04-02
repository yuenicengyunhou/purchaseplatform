package com.rails.purchaseplatform.user.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rails.purchaseplatform.common.ConRoute;
import com.alibaba.android.arouter.launcher.ARouter;
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
        onClick();

        binding.tabOrder.setNumber(3);
        binding.tabReceive.setNumber(100);
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
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_MODIFY_PAW)
                        .withString("url", ConRoute.WEB_URL.MODIFYPAW)
                        .navigation();
            }
        });

        binding.orderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ORDER.ORDER_MAIN).navigation();
            }
        });

        binding.tabOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.ORDER.ORDER_MAIN)
                        .navigation();
            }
        });

        binding.tabReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.ORDER.ORDER_MAIN)
                        .navigation();
            }
        });

        binding.tabSend.setOnClickListener(v -> {
                    ARouter.getInstance()
                            .build(ConRoute.ORDER.ORDER_MAIN)
                            .navigation();
                }

        );

        binding.browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_BROWSE)
                        .withString("url", ConRoute.WEB_URL.BROWSE)
                        .navigation();
            }
        });


        binding.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_COLLECT)
                        .withString("url", ConRoute.WEB_URL.COLLECT)
                        .navigation();
            }
        });


        binding.orderMeassge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.WEB.WEB_MSG).withString("url", ConRoute.WEB_URL.MSG).navigation();
            }
        });


        binding.myAddressAdministration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAIN).navigation();
            }
        });

        binding.llAudit.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到审批列表页面
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withString("url", ConRoute.WEB_URL.APPROVAL)
                    .navigation();
        });

        binding.llRejected.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到驳回页面
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withString("url", ConRoute.WEB_URL.APPROVAL)
                    .navigation();
        });

        binding.llPass.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到通过页面
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withString("url", ConRoute.WEB_URL.APPROVAL)
                    .navigation();
        });

    }
}
