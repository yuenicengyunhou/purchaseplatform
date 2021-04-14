package com.rails.purchaseplatform.order.activity;


import android.os.Bundle;
import android.view.View;

import androidx.core.widget.NestedScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.contract.OrderDetailsContract;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ActivityOrderDetailsBinding;

@Route(path = ConRoute.ORDER.ORDER_DETAILS)
public class OrderDetailsActivity extends ToolbarActivity<ActivityOrderDetailsBinding> implements OrderDetailsContract.DetailView {
    private final float px3 = ScreenSizeUtil.dp2px(this, 1100);
    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setTitleBar(R.string.order_detail_title)
                .setImgLeftRes(R.drawable.svg_back_black);
        barBinding.ivBackTop.setOnClickListener(v -> {
            barBinding.detailScorllView.smoothScrollTo(0,0);
            showOrHideBackButton(false);
        });
        barBinding.detailScorllView.setSmoothScrollingEnabled(true);
        barBinding.detailScorllView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > (int)px3) {
                showOrHideBackButton(true);
            }
        });
    }

    @Override
    protected int getColor() {
        return R.color.bg_blue;
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
    public void showOrHideBackButton(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        barBinding.ivBackTop.setVisibility(visibility);
    }
}