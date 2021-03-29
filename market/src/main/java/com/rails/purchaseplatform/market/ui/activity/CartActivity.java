package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ActivityMarketCartBinding;
import com.rails.purchaseplatform.market.ui.fragment.CartFrm;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */
public class CartActivity extends BaseErrorActivity<ActivityMarketCartBinding> {

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

        if (bundle == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CartFrm.newInstance(1))
                    .commitNow();
        }
    }
}