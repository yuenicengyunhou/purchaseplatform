package com.rails.purchaseplatform.user.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil2;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.FrmMineMallBinding;
import com.rails.purchaseplatform.user.ui.activity.SettingActivity;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.CONNECT_ERROR;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_PASTDUE;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_TIMEOUT;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD_2;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.HTTP_ERROR;

/**
 * 购物车--个人中心
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MineMallFrm extends LazyFragment<FrmMineMallBinding> implements UserToolContract.UserToolView {

    private UserToolContract.UserToolPresenter toolPresenter;

    private boolean isPurchase = false;
    private boolean isApprove = false;
    private boolean isCollect = false;
    private boolean isTrack = false;

    private int quitNum = 0;
    private String userName = "admin";

    @Override
    protected void loadData() {

        toolPresenter = new UserToolPresenterImpl(getActivity(), this);
    }

    @Override
    protected void loadPreVisitData() {
//        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue_2);


        PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        goLogin(hasToken());
        binding.tvWatch.setKey(String.format(getResources().getString(R.string.mine_seek), "0"));
        binding.tvCollect.setKey(String.format(getResources().getString(R.string.mine_collect), "0"));


        UserInfoBean bean = PrefrenceUtil.getInstance(getActivity()).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (bean != null) {
            toolPresenter.getUserInfoStatictics();
            toolPresenter.queryAuthor();
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    protected void onClick() {
        super.onClick();

        binding.tvLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
        });

        binding.btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasToken()) {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                    return;
                }
                ARouter.getInstance().build(ConRoute.WEB.WEB_MSG).withString("url", ConRoute.WEB_URL.MSG).navigation();
            }
        });


        binding.btnSetting.setOnClickListener(v -> {
            if (!hasToken()) {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                return;
            }
            startIntent(SettingActivity.class);
        });


        binding.orderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasToken()) {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                    return;
                }

                if (!isPurchase) {
                    ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                    return;
                }
                ARouter.getInstance().build(ConRoute.ORDER.ORDER_MAIN).navigation();
            }
        });

        binding.tabOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasToken()) {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                    return;
                }
                //待下单
                if (!isPurchase) {
                    ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                    return;
                }
                ARouter.getInstance()
                        .build(ConRoute.ORDER.ORDER_MAIN)
                        .withString("statusCode", "10")
                        .navigation();
            }
        });

        binding.tabRecivice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasToken()) {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                    return;
                }
                //待收货 "status": "待收货", "statusCode": "30"
                if (!isPurchase) {
                    ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                    return;
                }
                ARouter.getInstance()
                        .build(ConRoute.ORDER.ORDER_MAIN)
                        .withString("statusCode", "30")
                        .navigation();
            }
        });

        binding.tabSend.setOnClickListener(v -> {
                    if (!hasToken()) {
                        ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                        return;
                    }
                    //待发货 "status": "待发货", "statusCode": "20"
                    if (!isPurchase) {
                        ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                        return;
                    }
                    ARouter.getInstance()
                            .build(ConRoute.ORDER.ORDER_MAIN)
                            .withString("statusCode", "20")
                            .navigation();
                }
        );

        binding.tabQuit.setOnClickListener(v -> {
            if (!hasToken()) {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                return;
            }
            //待发货 "status": "已经取消", "statusCode": "70"
            if (!isPurchase) {
                ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                return;
            }
            PrefrenceUtil2.getInstance(getActivity()).setString(userName, String.valueOf(quitNum));
            ARouter.getInstance()
                    .build(ConRoute.ORDER.ORDER_MAIN)
                    .withString("statusCode", "70")
                    .navigation();
        });

        binding.tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasToken()) {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                    return;
                }
                if (!isTrack) {
                    ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                    return;
                }
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_BROWSE)
                        .withString("url", ConRoute.WEB_URL.BROWSE)
                        .navigation();
            }
        });


        binding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasToken()) {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                    return;
                }
                if (!isCollect) {
                    ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                    return;
                }
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_COLLECT)
                        .withString("url", ConRoute.WEB_URL.COLLECT)
                        .navigation();
            }
        });


        binding.llAudit.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到审批列表页面
            if (!hasToken()) {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                return;
            }
            if (!isApprove) {
                ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                return;
            }
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withString("url", ConRoute.WEB_URL.APPROVAL)
                    .navigation();
        });

        binding.llRejected.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到驳回页面
            if (!hasToken()) {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                return;
            }
            if (!isApprove) {
                ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                return;
            }
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_APPROVAL)
                    .withInt("type", 2)
                    .withString("url", ConRoute.WEB_URL.TURN_DOWN_LIST)
                    .navigation();
        });

        binding.llPass.setOnClickListener(v -> {
            // TODO: 2021/4/1 跳转到通过页面
            if (!hasToken()) {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                return;
            }
            if (!isApprove) {
                ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                return;
            }
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

        quitNum = bean.getFailureCount();
        binding.tabOrder.setNumber(bean.getStayPayCount());
        binding.tabSend.setNumber(bean.getStayDeliverCount());
        binding.tabRecivice.setNumber(bean.getStayReceiveCount());

        String num = PrefrenceUtil2.getInstance(getActivity()).getString(userName, "");
        if (num.equals(String.valueOf(quitNum)))
            binding.tabQuit.setNumber(0);
        else
            binding.tabQuit.setNumber(quitNum);
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {
        setStaticticsInfo(bean);
    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {
        setUserInfo(bean);
    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {

    }

    @Override
    public void getAuthor(AuthorBean authorBean) {
        this.isPurchase = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.MENU_PURCHAR, false);
        this.isApprove = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.MENU_APPROVE, false);
        this.isCollect = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.MENU_COLLECT, false);
        this.isTrack = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.MENU_TRACK, false);
    }


    /**
     * 设置用户信息
     *
     * @param bean
     */
    private void setUserInfo(UserStatisticsBean bean) {
        if (bean == null)
            return;
        goLogin(true);

        toolPresenter.getUserStatictics();

        userName = bean.getUserName();
        binding.tvName.setText(String.valueOf(bean.getUserName()));
        binding.tvDepartment.setText(bean.getDepartmentOrganizationName());

        binding.tvWatch.setKey(String.format(getResources().getString(R.string.mine_seek), bean.getVisitTrackCount()));
        binding.tvCollect.setKey(String.format(getResources().getString(R.string.mine_collect), bean.getCollectCount()));
    }


    @Override
    public void onError(ErrorBean errorBean) {
        String errorCode = errorBean.getCode();
        switch (errorCode) {
            case ERROR_PASTDUE:
            case ERROR_UNLOAD:
            case HTTP_ERROR:
            case ERROR_UNLOAD_2:
            case ERROR_TIMEOUT: {
                PrefrenceUtil.getInstance(getActivity()).setString(ConShare.TOKEN, "");
                goLogin(false);
            }
            break;
            case "500": {
                ToastUtil.showCenter(getActivity(), errorBean.getMsg());
            }
            break;
        }
    }


    private void goLogin(boolean isLogin) {
        if (isLogin) {
            binding.tvName.setVisibility(View.VISIBLE);
            binding.tvDepartment.setVisibility(View.VISIBLE);
            binding.tvLogin.setVisibility(View.GONE);
        } else {
            binding.tvName.setVisibility(View.GONE);
            binding.tvDepartment.setVisibility(View.GONE);
            binding.tvLogin.setVisibility(View.VISIBLE);
        }
    }


    /**
     * token是否存在
     *
     * @return
     */
    private boolean hasToken() {
        String token = PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        if (TextUtils.isEmpty(token))
            return false;
        else
            return true;
    }

}
