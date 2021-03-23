package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ActivityMarketBinding;

/**
 * 店铺详情
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */
public class ShopDetailActivity extends ToolbarActivity<ActivityMarketBinding> {
    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_shop);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
