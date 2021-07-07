package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;
import android.view.View;

import com.rails.purchaseplatform.common.databinding.DialogPermissionBinding;
import com.rails.purchaseplatform.framwork.base.BasePop;

public class PermissionPop extends BasePop<DialogPermissionBinding> {
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.btnAllow.setOnClickListener(v ->{
            if (null != onClickListener) {
                onClickListener.onClick(binding.btnAllow);
            }
            dismiss();
        });
    }

}
