package com.rails.purchaseplatform.order.pop;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.order.adapter.OrderSearchFilterAdapter;
import com.rails.purchaseplatform.order.databinding.PopOrderSearchFilterBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 采购单列表页筛选按钮弹窗
 */
public class OrderSearchFilterPop extends BasePop<PopOrderSearchFilterBinding> {

    private OrderSearchFilterAdapter mAdapter;

    @Override
    protected void initialize(Bundle bundle) {

        mAdapter = new OrderSearchFilterAdapter(getActivity());

        binding.recyclerStatus.setLayoutManager(new FlowLayoutManager());
        binding.recyclerStatus.addItemDecoration(new SpaceItemDecoration(10));
        binding.recyclerStatus.setAdapter(mAdapter);

        Type type = new TypeToken<ArrayList<OrderStatusBean>>() {
        }.getType();
        ArrayList<OrderStatusBean> beans = JsonUtil.parseJson(getActivity(), "orderStatus.json", type);

        mAdapter.update(beans, true);

        binding.tvReset.setOnClickListener(v -> dismiss());
        binding.tvComplete.setOnClickListener(v -> dismiss());

    }
}
