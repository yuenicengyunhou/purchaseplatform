package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.databinding.ActivityImageDetailsBinding;

public class ImageDetailsActivity extends BaseErrorActivity<ActivityImageDetailsBinding> {


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
}