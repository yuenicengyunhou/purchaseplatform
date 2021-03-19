package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.databinding.ActivityItemDetailsBinding;

public class ItemDetailsActivity extends BaseErrorActivity<ActivityItemDetailsBinding> {


    @Override
    protected void initialize(Bundle bundle) {

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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
