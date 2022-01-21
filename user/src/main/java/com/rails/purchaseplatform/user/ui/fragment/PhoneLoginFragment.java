package com.rails.purchaseplatform.user.ui.fragment;

import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import androidx.core.content.res.ResourcesCompat;

import com.rails.lib_data.contract.LoginContract;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.user.adapter.LoginInfoAdapter;
import com.rails.purchaseplatform.user.databinding.FragmentLoginPhoneBinding;

import java.text.MessageFormat;
import java.util.ArrayList;

public class PhoneLoginFragment extends LazyFragment<FragmentLoginPhoneBinding>
        implements LoginInfoAdapter.FillPhoneListener {

    final private String TAG = PhoneLoginFragment.class.getSimpleName();

    private RandomCodeLoginFragment.ScrollUpListener mScrollUpListener;

    private InputMethodManager mManager;

    private LoginContract.LoginPresenter mPresenter;

    private ArrayList<String> mPhoneList = new ArrayList<>();

    private PopupWindow mPop;

    Drawable drawableHasFocus;

    Drawable drawableLoseFocus;


    @Override
    public void onStart() {
        super.onStart();
        drawableHasFocus = ResourcesCompat.getDrawable(
                getResources(),
                com.rails.purchaseplatform.common.R.drawable.bg_corner_blue_5,
                null);
        drawableLoseFocus = ResourcesCompat.getDrawable(
                getResources(),
                com.rails.purchaseplatform.common.R.drawable.bg_corner_gray_5,
                null);
    }

    @Override
    protected void loadData() {

        if (mPhoneList.size() != 0) {
            if (mPhoneList.get(0).contains("。")) {
                binding.etPhoneInput.setText(mPhoneList.get(0).split("。")[0]);
                binding.etPasswordInput.setText(mPhoneList.get(0).split("。")[1]);
            } else {
                binding.etPhoneInput.setText(mPhoneList.get(0));
                binding.etPasswordInput.setText("");
            }
        }

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

        binding.etPhoneInput.setOnClickListener(v -> {
            if (!mPop.isShowing()) showLoginInfoList();
        });

        // 获得/丢失焦点时更改横线颜色 页面滚动
        binding.etPhoneInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewPhoneInputLine);
            scrollUp("phone", v, hasFocus);
            if (mPop != null && !mPop.isShowing() && hasFocus) {
                showLoginInfoList();
            }
        });
        binding.etPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewPasswordInputLine);
            scrollUp("password", v, hasFocus);
        });
        binding.etVerifyNumInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewVerifyNumInputLine);
            scrollUp("verifyCode", v, hasFocus);
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

    public void setManager(InputMethodManager manager) {
        this.mManager = manager;
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

    public void setPop(PopupWindow pop) {
        this.mPop = pop;
    }

    public void showLoginInfoList() {
        if (mPhoneList.size() != 0)
            mPop.showAsDropDown(binding.etPhoneInput, 0, ScreenSizeUtil.dp2px(requireActivity(), 8F));
    }


    /**
     * 设置输入框下方的线颜色变化
     *
     * @param hasFocus 是否有焦点
     * @param view     需要改变颜色的View
     */
    private void setInputLineBackground(boolean hasFocus, View view) {
        if (hasFocus)
            view.setBackground(drawableHasFocus);
        else
            view.setBackground(drawableLoseFocus);
    }


    public void setLoginPhoneList(ArrayList<String> phoneList) {
        this.mPhoneList = phoneList;
    }


    /**
     * 失去焦点时收回键盘
     * 获得焦点时根据flag滚动到指定位置
     *
     * @param flag
     * @param v
     * @param hasFocus
     */
    private void scrollUp(String flag, View v, boolean hasFocus) {
        if (mManager != null && !hasFocus)
            mManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (mScrollUpListener != null && hasFocus) mScrollUpListener.scrollUp(flag);
    }


    /**
     * @param count
     */
    public void setCountDown(int count) {
        if (count == 60) {
            binding.tvCountDown.setVisibility(View.VISIBLE);
            binding.tvGetVerifyNum.setVisibility(View.INVISIBLE);
        }
        binding.tvCountDown.setText(MessageFormat.format("{0}s", count));
        if (count <= 0) {
            binding.tvCountDown.setVisibility(View.GONE);
            binding.tvGetVerifyNum.setVisibility(View.VISIBLE);
        }
    }


    public void setNeedScrollUpListener(RandomCodeLoginFragment.ScrollUpListener listener) {
        this.mScrollUpListener = listener;
    }

    @Override
    public void fillPhone(String phone) {
        if (mPhoneList.get(0).contains("。")) {
            String truePhone = phone.split("。")[0];
            String password = phone.split("。")[1];
            binding.etPhoneInput.setText(truePhone);
            binding.etPasswordInput.setText(password);
        } else {
            binding.etPhoneInput.setText(mPhoneList.get(0));
            binding.etPasswordInput.setText("");
        }
        mPop.dismiss();
    }

    public interface ScrollUpListener {
        void scrollUp(String flag);
    }
}
