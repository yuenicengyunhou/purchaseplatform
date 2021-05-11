package com.rails.purchaseplatform.user.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.contract.RetrievePasswordContract;
import com.rails.lib_data.contract.RetrievePasswordPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.databinding.ActivityRetrievePasswordBinding;

/**
 * @author ZhangXiaofu
 * @version 1
 * <p>
 * 找回密码页面
 * 输入用户名和邮箱进行验证
 * @since 2021.03.28
 */
public class RetrievePasswordActivity extends BaseErrorActivity<ActivityRetrievePasswordBinding> implements RetrievePasswordContract.RetrievePasswordView {

    private RetrievePasswordContract.RetrievePasswordPresenter mPresenter;

    @Override
    protected void initialize(Bundle bundle) {
        mPresenter = new RetrievePasswordPresenterImpl(this, this);
    }

    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onClick() {
        super.onClick();
        binding.ibBack.setOnClickListener(v -> finish());

        binding.btnConfirm.setOnClickListener(v -> {
            String username = binding.etUserNameInput.getText().toString().trim(),
                    email = binding.etEmailInput.getText().toString().trim();
            email = "1023725142@qq.com";
            username = "lwq010";
            mPresenter.retrievePassword(username, email, true);
        });

        binding.etEmailInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewUserEmailInputLine));
        binding.etUserNameInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewUserNameInputLine));
    }

    /**
     * 设置输入框下方的线颜色变化
     *
     * @param hasFocus 是否有焦点
     * @param view     需要改变颜色的View
     */
    private void setInputLineBackground(boolean hasFocus, View view) {
        Drawable drawableHasFocus = getResources().getDrawable(
                com.rails.purchaseplatform.common.R.drawable.bg_corner_blue_5);
        Drawable drawableLoseFocus = getResources().getDrawable(
                com.rails.purchaseplatform.common.R.drawable.bg_corner_gray_5);
        if (hasFocus)
            view.setBackground(drawableHasFocus);
        else
            view.setBackground(drawableLoseFocus);
    }

    @Override
    public void onRetrieveSuccess(String message) {
        ToastUtil.showCenter(this, message);
        Bundle bundle = new Bundle();
        bundle.putString("email", binding.etEmailInput.getText().toString().trim());
        ARouter.getInstance()
                .build(ConRoute.USER.RETRIEVE_PASSWORD)
                .with(bundle).navigation();
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}