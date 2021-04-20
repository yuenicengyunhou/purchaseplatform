package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.pop.AlterDialog;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivitySettingBinding;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

/**
 * 设置页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/9
 */
public class SettingActivity extends ToolbarActivity<ActivitySettingBinding> {


    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar
                .setTitleBar(R.string.user_setting)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);

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
                    .leftBtn("取消")
                    .rightBtn("确定")
                    .setDialogListener(new AlterDialog.DialogListener() {
                        @Override
                        public void onLeft() {
                            dismissDialog();
                        }

                        @Override
                        public void onRight() {
                            PrefrenceUtil.getInstance(SettingActivity.this).setString(ConShare.TOKEN, "");
                            ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
                            dismissDialog();
                        }
                    })
                    .builder().show();
        });

    }
}
