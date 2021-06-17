package com.rails.purchaseplatform.user.ui.fragment;

import android.view.View;

import com.rails.lib_data.contract.LoginContract;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.user.databinding.FragmentLoginPhoneBinding;

import java.util.ArrayList;

public class PhoneLoginFragment extends LazyFragment<FragmentLoginPhoneBinding> {
    final private String TAG = PhoneLoginFragment.class.getSimpleName();

    private LoginContract.LoginPresenter mPresenter;

    @Override
    protected void loadData() {
        binding.tvGetVerifyNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCode(binding.tvGetVerifyNum.getText().toString().trim());
            }
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
}
