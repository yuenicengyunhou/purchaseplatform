package com.rails.purchaseplatform.user.ui.activity;


import android.os.Bundle;

import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivityModifyPswBinding;

public class ModifyPswActivity extends BaseErrorActivity<ActivityModifyPswBinding> {

    @Override
    protected void initialize(Bundle bundle) {

    }

    @Override
    protected int getColor() {
        return R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }
}