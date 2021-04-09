package com.rails.purchaseplatform.user.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.FrmMineMallBinding;
import com.rails.purchaseplatform.user.ui.activity.SettingActivity;

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

        binding.tabOrder.setNumber(100);
        binding.tabReceive.setNumber(3);
        binding.tabSend.setNumber(50);


        binding.tvWatch.setKey(String.format(getResources().getString(R.string.mine_seek), "30"));
        binding.tvCollect.setKey(String.format(getResources().getString(R.string.mine_collect), "30"));
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

        binding.btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.WEB.WEB_MSG).withString("url", ConRoute.WEB_URL.MSG).navigation();
            }
        });


        binding.btnSetting.setOnClickListener(v -> {
            startIntent(SettingActivity.class);
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

        binding.tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_BROWSE)
                        .withString("url", ConRoute.WEB_URL.BROWSE)
                        .navigation();
            }
        });


        binding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_COLLECT)
                        .withString("url", ConRoute.WEB_URL.COLLECT)
                        .navigation();
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
                    .withString("url", ConRoute.WEB_URL.TURN_DOWN_LIST)
                    .navigation();
        });

        binding.llPass.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到通过页面
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withString("url", ConRoute.WEB_URL.PASS_LIST)
                    .navigation();
        });

    }
}
