package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.pop.AlterDialog;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivitySettingBinding;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

import java.util.Set;

/**
 * 设置页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/9
 */
public class SettingActivity extends ToolbarActivity<ActivitySettingBinding> {


    private boolean isAddressManager = false;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar
                .setTitleBar(R.string.user_setting)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);

        isAddressManager = PrefrenceUtil.getInstance(this).getBoolean(ConShare.MENU_ADDRESS, false);

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

        barBinding.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAddressManager) {
                    ToastUtil.showCenter(SettingActivity.this, getResources().getString(R.string.common_author_null));
                    return;
                }
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAIN).navigation();
            }
        });


        barBinding.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_MODIFY_PAW)
                        .withString("url", ConRoute.WEB_URL.MODIFYPAW)
                        .navigation();
            }
        });


        barBinding.btnExit.setOnClickListener(v -> {
            new AlterDialog.Builder().context(this)
                    .title("提示")
                    .msg("确定要退出吗")
                    .leftBtn("确定")
                    .rightBtn("取消")
                    .setDialogListener(new AlterDialog.DialogListener() {
                        @Override
                        public void onLeft() {
                            PrefrenceUtil.getInstance(SettingActivity.this).clear();
                            ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                            dismissDialog();
                            finish();
                        }

                        @Override
                        public void onRight() {
                            dismissDialog();
                        }
                    })
                    .builder().show();
        });

    }
}
