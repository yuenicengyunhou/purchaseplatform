package com.rails.purchaseplatform.order.activity;


import android.os.Bundle;

import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.order.databinding.ActivityDeliveredBinding;

public class DeliveredActivity extends ToolbarActivity<ActivityDeliveredBinding> {


    @Override
    protected void initialize(Bundle bundle) {

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