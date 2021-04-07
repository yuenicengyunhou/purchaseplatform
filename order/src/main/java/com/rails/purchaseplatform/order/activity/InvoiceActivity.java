package com.rails.purchaseplatform.order.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ActivityOrderInvoiceBinding;

/**
 * 发票页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
public class InvoiceActivity extends ToolbarActivity<ActivityOrderInvoiceBinding> {
    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setTitleBar(R.string.order_invoice)
                .setImgLeftRes(R.drawable.svg_back_black);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
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
