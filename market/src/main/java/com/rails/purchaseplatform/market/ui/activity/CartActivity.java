package com.rails.purchaseplatform.market.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ActivityMarketCartBinding;
import com.rails.purchaseplatform.market.ui.fragment.CartFrm;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */
public class CartActivity extends ToolbarActivity<ActivityMarketCartBinding> {

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
    protected void initialize(Bundle bundle) {

        binding.titleBar
                .setTitleBar(R.string.market_cart)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);

        if (bundle == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CartFrm.newInstance(1))
                    .commitNow();
        }
    }
}