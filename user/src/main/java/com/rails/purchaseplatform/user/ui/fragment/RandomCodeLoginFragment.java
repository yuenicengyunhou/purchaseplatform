package com.rails.purchaseplatform.user.ui.fragment;

import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.View;

import com.bumptech.glide.Glide;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.http.HttpConstants;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.framwork.http.observer.BaseRetrofit;
import com.rails.purchaseplatform.framwork.utils.MD5Util;
import com.rails.purchaseplatform.user.databinding.FragmentLoginRandomCodeBinding;

import java.util.ArrayList;
import java.util.UUID;

public class RandomCodeLoginFragment extends LazyFragment<FragmentLoginRandomCodeBinding> {
    final private String TAG = RandomCodeLoginFragment.class.getSimpleName();

    private ScrollUpListener mScrollUpListener;

    private LoginContract.LoginPresenter mPresenter;
    private String url = HttpConstants.DEBUG_PLATFORM_URL;

    @Override
    protected void loadData() {
        if (BaseRetrofit.isDebug) {
            binding.etAccountInput.setText("cezdbj001");
            binding.etPasswordInput.setText("Pass!word@1234");
            binding.et1.setText("000");
            binding.et2.setText("000");
            binding.et3.setText("000");
        }

        // 刷新随机码坐标
        binding.ibReloadRandomCode.setOnClickListener(v ->
                mPresenter.randomInit(MD5Util.MD5(UUID.randomUUID().toString() + System.currentTimeMillis()), true));

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
        binding.etAccountInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewAccountInputLine));
        binding.etPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewPasswordInputLine);
            // 输入密码框获得焦点时 整个页面上移
            if (mScrollUpListener != null && hasFocus) mScrollUpListener.scrollUp();
        });


        // 坐标输入框获得焦点时 整个页面上移
        binding.et1.setOnFocusChangeListener((v, hasFocus) -> {
            if (mScrollUpListener != null && hasFocus) mScrollUpListener.scrollUp();
        });
        binding.et2.setOnFocusChangeListener((v, hasFocus) -> {
            if (mScrollUpListener != null && hasFocus) mScrollUpListener.scrollUp();
        });
        binding.et3.setOnFocusChangeListener((v, hasFocus) -> {
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
        infoList.add(binding.etAccountInput.getText().toString().trim());
        infoList.add(binding.etPasswordInput.getText().toString().trim());
        infoList.add(binding.et1.getText().toString().trim());
        infoList.add(binding.et2.getText().toString().trim());
        infoList.add(binding.et3.getText().toString().trim());
        return infoList;
    }

    public void setRandInit(String randInit) {
        if (!BaseRetrofit.isDebug) url = HttpConstants.PLATFORM_URL;

        Glide.with(requireActivity())
                .load(url + "passport/coordinate/v1/" + randInit)
                .into(binding.iv1);

        Glide.with(requireActivity())
                .load(url + "passport/coordinate/v2/" + randInit)
                .into(binding.iv2);

        Glide.with(requireActivity())
                .load(url + "passport/coordinate/v3/" + randInit)
                .into(binding.iv3);
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

    public void setNeedScrollUpListener(ScrollUpListener listener) {
        this.mScrollUpListener = listener;
    }

    public interface ScrollUpListener {
        void scrollUp();
    }
}
