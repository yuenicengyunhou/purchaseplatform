package com.rails.purchaseplatform.user.ui.fragment;

import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.View;

import com.rails.lib_data.contract.LoginContract;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.user.databinding.FragmentLoginPhoneBinding;

import java.util.ArrayList;

public class PhoneLoginFragment extends LazyFragment<FragmentLoginPhoneBinding> {
    final private String TAG = PhoneLoginFragment.class.getSimpleName();

    private RandomCodeLoginFragment.ScrollUpListener mScrollUpListener;

    private LoginContract.LoginPresenter mPresenter;

    @Override
    protected void loadData() {
        // 请求验证码
        binding.tvGetVerifyNum.setOnClickListener(v ->
                mPresenter.getCode(binding.etPhoneInput.getText().toString().trim()));

        // 点击显示/隐藏密码更改图标状态
        binding.rlPasswordVisible.setOnClickListener(v -> {
            boolean isChecked = binding.cbPasswordVisible.isChecked();
            binding.cbPasswordVisible.setChecked(!isChecked);
        });

        // 显示/隐藏密码状态更改时 文字状态也更改
        binding.cbPasswordVisible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.etPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                binding.etPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        // 获得/丢失焦点时更改横线颜色
        binding.etPhoneInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewPhoneInputLine));
        binding.etPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewPasswordInputLine);
            // 输入密码获得焦点时 将整个页面上移
            if (mScrollUpListener != null && hasFocus) mScrollUpListener.scrollUp();
        });
        binding.etVerifyNumInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewVerifyNumInputLine);
            // 输入验证码获得焦点时 将整个页面上移
            if (mScrollUpListener != null && hasFocus) mScrollUpListener.scrollUp();
        });
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    public void setPresenter(LoginContract.LoginPresenter presenter) {
        mPresenter = presenter;
    }

    public ArrayList<String> getLoginInfo() {
        ArrayList<String> infoList = new ArrayList<>();
        infoList.add(binding.etPhoneInput.getText().toString().trim());
        infoList.add(binding.etPasswordInput.getText().toString().trim());
        infoList.add(binding.etVerifyNumInput.getText().toString().trim());
        return infoList;
    }

    public void setVerifyCode(String code) {
        binding.etVerifyNumInput.setText(code);
    }


    /**
     * 设置输入框下方的线颜色变化
     *
     * @param hasFocus 是否有焦点
     * @param view     需要改变颜色的View
     */
    private void setInputLineBackground(boolean hasFocus, View view) {
        Drawable drawableHasFocus = getResources().getDrawable(com.rails.purchaseplatform.common.R.drawable.bg_corner_blue_5);
        Drawable drawableLoseFocus = getResources().getDrawable(com.rails.purchaseplatform.common.R.drawable.bg_corner_gray_5);
        if (hasFocus)
            view.setBackground(drawableHasFocus);
        else
            view.setBackground(drawableLoseFocus);
    }


    public void setNeedScrollUpListener(RandomCodeLoginFragment.ScrollUpListener listener) {
        this.mScrollUpListener = listener;
    }

    public interface ScrollUpListener {
        void scrollUp();
    }
}
