package com.rails.purchaseplatform.order;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.order.databinding.ActivityOrderVerityBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 核实订单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/27
 */
@Route(path = ConRoute.ORDER.ORDER_VERITY)
public class OrderVerityActivity extends ToolbarActivity<ActivityOrderVerityBinding> {
    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setTitleBar(R.string.order_verify)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);


//        cartAdapter = new CartAdapter(getActivity());
//        cartAdapter.setListener(this);
//        cartAdapter.setMulPositionListener(this);
//        barBinding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
//        barBinding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.line_gray));
//        barBinding.recycler.setAdapter(cartAdapter);
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
