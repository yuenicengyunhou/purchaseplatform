package com.rails.purchaseplatform.user.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
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
public class MineMallFrm extends LazyFragment<FrmMineMallBinding> implements UserToolContract.UserToolView {

    private UserToolContract.UserToolPresenter toolPresenter;
    private UserInfoBean bean;

    @Override
    protected void loadData() {

        bean = PrefrenceUtil.getInstance(getActivity()).getBean(ConShare.USERINFO, UserInfoBean.class);
        toolPresenter = new UserToolPresenterImpl(getActivity(), this);

    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
        if (bean != null) {
            toolPresenter.getUserStatictics(bean.getId(), bean.getAccountType());
            toolPresenter.getUserInfoStatictics(bean.getId(), bean.getAccountType());
        }
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
                //待下单
                ARouter.getInstance()
                        .build(ConRoute.ORDER.ORDER_MAIN)
                        .withString("statusCode", "10")
                        .navigation();
            }
        });

        binding.tabRecivice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //待收货 "status": "待收货", "statusCode": "30"
                ARouter.getInstance()
                        .build(ConRoute.ORDER.ORDER_MAIN)
                        .withString("statusCode", "30")
                        .navigation();
            }
        });

        binding.tabSend.setOnClickListener(v -> {
                    //待发货 "status": "待发货", "statusCode": "20"
                    ARouter.getInstance()
                            .build(ConRoute.ORDER.ORDER_MAIN)
                            .withString("statusCode", "20")
                            .navigation();
                }
        );

        binding.tabQuit.setOnClickListener(v -> {
            //待发货 "status": "已经取消", "statusCode": "70"
            ARouter.getInstance()
                    .build(ConRoute.ORDER.ORDER_MAIN)
                    .withString("statusCode", "70")
                    .navigation();
        });

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
                    .withInt("type", 2)
                    .withString("url", ConRoute.WEB_URL.TURN_DOWN_LIST)
                    .navigation();
        });

        binding.llPass.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到通过页面
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withInt("type", 1)
                    .withString("url", ConRoute.WEB_URL.TURN_DOWN_LIST)
                    .navigation();
        });

    }


    /**
     * 设置统计信息
     *
     * @param bean
     */
    private void setStaticticsInfo(UserStatisticsBean bean) {
        if (bean == null)
            return;

        binding.tabOrder.setNumber(bean.getStayDeliverCount());
        binding.tabSend.setNumber(bean.getStayDeliverCount());
        binding.tabRecivice.setNumber(bean.getStayReceiveCount());
        binding.tabQuit.setNumber(bean.getFailureCount());


        binding.llAudit.setNumber(bean.getStayAuditCount());
        binding.llPass.setNumber(bean.getPassedCount());
        binding.llRejected.setNumber(bean.getRejectCount());


    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {
        setStaticticsInfo(bean);
    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {
        setUserInfo(bean);
    }


    /**
     * 设置用户信息
     *
     * @param bean
     */
    private void setUserInfo(UserStatisticsBean bean) {
        if (bean == null)
            return;

        binding.tvName.setText(String.valueOf(bean.getUserName()));
        binding.tvDepartment.setText(bean.getDepartmentOrganizationName());

        binding.tvWatch.setKey(String.format(getResources().getString(R.string.mine_seek), bean.getVisitTrackCount()));
        binding.tvCollect.setKey(String.format(getResources().getString(R.string.mine_collect), bean.getCollectCount()));
    }
}
