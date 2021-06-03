package com.rails.purchaseplatform.user.ui.activity;


import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.contract.PswContract;
import com.rails.lib_data.contract.PswPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivityModifyPswBinding;

@Route(path = ConRoute.WEB.WEB_MODIFY_PAW)
public class ModifyPswActivity extends ToolbarActivity<ActivityModifyPswBinding> implements PswContract.PassWordView {

    private PswContract.PswPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar
                .setTitleBar(R.string.empty_title)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);

        presenter = new PswPresenterImpl(this, this);

//        barBinding.etOld.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        barBinding.etNew.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        barBinding.etNewAgain.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);


        barBinding.tvCommit.setOnClickListener(v -> getAllPassWord());
    }

    @Override
    protected void onClick() {
        setOnPasswordVisibleListener(barBinding.viewOld, barBinding.eyeOld, barBinding.etOld);
        setOnPasswordVisibleListener(barBinding.viewNew, barBinding.eyeNew, barBinding.etNew);
        setOnPasswordVisibleListener(barBinding.viewAgain, barBinding.eyeNewAgain, barBinding.etNewAgain);
    }

    private void setOnPasswordVisibleListener(View view, CheckBox checkBox, EditText editText) {
        view.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    @Override
    public void getAllPassWord() {
        String oldPsw = barBinding.etOld.getText().toString().trim();
        String newPsw = barBinding.etNew.getText().toString().trim();
        String againPsw = barBinding.etNewAgain.getText().toString().trim();
        presenter.verifyToCommit(oldPsw, newPsw, againPsw);
    }

    @Override
    public void finishPage() {
        finish();
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
}