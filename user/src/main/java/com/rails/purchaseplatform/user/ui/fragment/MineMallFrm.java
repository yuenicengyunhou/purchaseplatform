package com.rails.purchaseplatform.user.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.FrmMineMallBinding;
import com.rails.purchaseplatform.user.ui.activity.SettingActivity;

/**
 * 购物车--个人中心
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MineMallFrm extends LazyFragment<FrmMineMallBinding> implements LoginContract.LoginView {

    private LoginContract.LoginPresenter presenter;
    private String token;

    @Override
    protected void loadData() {

        token = PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        presenter = new LoginPresneterImpl(getActivity(), this);

    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
        presenter.getUserInfo(false, token);
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

    @Override
    public void onResult(int type, String msg, String token) {

    }

    @Override
    public void getUserInfo(UserInfoBean bean) {
        PrefrenceUtil.getInstance(getActivity()).setBean(ConShare.USERINFO, bean);
        setUserInfo(bean);
    }


    /**
     * 设置用户信息
     *
     * @param bean
     */
    private void setUserInfo(UserInfoBean bean) {
        if (bean == null)
            return;

        binding.tvName.setText(String.valueOf(bean.getUserName()));
        binding.tvDepartment.setText(bean.getDepartmentOrganizationName());

        binding.tabOrder.setNumber(100);
        binding.tabReceive.setNumber(3);
        binding.tabSend.setNumber(50);


        binding.tvWatch.setKey(String.format(getResources().getString(R.string.mine_seek), "30"));
        binding.tvCollect.setKey(String.format(getResources().getString(R.string.mine_collect), "30"));
    }
}
