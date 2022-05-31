package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.pop.AlterDialog;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivitySettingBinding;

/**
 * 设置页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/9
 */
public class SettingActivity extends ToolbarActivity<ActivitySettingBinding> implements UserToolContract.UserToolView {


    private boolean isAddressManager = false;
    private UserToolContract.UserToolPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar
                .setTitleBar(R.string.user_setting)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);

        presenter = new UserToolPresenterImpl(this, this);
        presenter.queryAuthor();

        String version = SystemUtil.getVersionName(this);
        barBinding.tvVersion.setText("V" + version);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    protected void onClick() {
        super.onClick();

        barBinding.btnAddress.setOnClickListener(v -> {
            if (!isAddressManager) {
                ToastUtil.showCenter(SettingActivity.this, getResources().getString(R.string.common_author_null));
                return;
            }
            ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAIN).navigation();
        });


        barBinding.btnModify.setOnClickListener(v -> ARouter.getInstance()
                .build(ConRoute.USER.USER_MODIFY_PAW)
                .withString("url", ConRoute.WEB_URL.MODIFYPAW)
                .navigation());

        barBinding.btnAboutUs.setOnClickListener(v -> ARouter.getInstance()
                .build(ConRoute.USER.USER_ABOUT_US)
                .navigation());


        barBinding.btnExit.setOnClickListener(v -> new AlterDialog.Builder().context(this)
                .title("提示")
                .msg("确定要退出吗")
                .leftBtn("确定")
                .rightBtn("取消")
                .setDialogListener(new AlterDialog.DialogListener() {
                    @Override
                    public void onLeft() {
                        PrefrenceUtil.getInstance(SettingActivity.this).clear();
                        SettingActivity.this
                                .getSharedPreferences("SEARCH_HISTORY", MODE_PRIVATE)
                                .edit().clear().apply();
                        BaseActManager.getInstance().clear();
                        ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                        dismissDialog();
                        finish();
                    }

                    @Override
                    public void onRight() {
                        dismissDialog();
                    }
                })
                .builder().show());

    }


    @Override
    protected void reNetLoad() {
        super.reNetLoad();
        presenter.queryAuthor();
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {

    }

    @Override
    public void getAuthor(AuthorBean authorBean) {
        isAddressManager = PrefrenceUtil.getInstance(this).getBoolean(ConShare.MENU_ADDRESS, false);
    }
}
