package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.OrderSearchFilterAdapter2;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.databinding.PopOrderSearchFilterBinding;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 采购单列表页筛选按钮弹窗
 */
public class OrderSearchFilterPop extends BasePop<PopOrderSearchFilterBinding> {

//    private OrderSearchFilterAdapter mAdapter;

    private OrderSearchFilterAdapter2 mAdapter2;
    private ArrayList<String> mData;
    private FlowLayoutManager mManager;
    private String[] mTexts;

    public OrderSearchFilterPop() {
    }

    public OrderSearchFilterPop(String[] text) {
        mTexts = text;
    }

    @Override
    protected void initialize(Bundle bundle) {
        mData = new ArrayList<>();
        mData.add("全部");
        mData.add("待下单");
        mData.add("已驳回");
        mData.add("待发货");
        mData.add("已发货");
        mData.add("待收货");
        mData.add("已取消");
        mData.add("已收货");
        mData.add("采购人取消");

//        mAdapter = new OrderSearchFilterAdapter(getActivity());
        mAdapter2 = new OrderSearchFilterAdapter2(getActivity(), mData);

        binding.recyclerStatus.setLayoutManager(new FlowLayoutManager());
        binding.recyclerStatus.addItemDecoration(new SpaceItemDecoration(20));
        binding.recyclerStatus.setAdapter(mAdapter2);

        Type type = new TypeToken<ArrayList<OrderStatusBean>>() {
        }.getType();
        ArrayList<OrderStatusBean> beans = JsonUtil.parseJson(getActivity(), "orderStatus.json", type);

//        mAdapter.update(beans, true);

        binding.tvReset.setOnClickListener(v -> dismiss());
        binding.tvComplete.setOnClickListener(v -> dismiss());

        setText(mTexts);

    }

    public void setText(String[] texts) {
        if (texts.length > 0) {
            binding.tvStatus.setText(texts[0]);
            binding.tvPrice.setText(texts[1]);
            binding.tvTime.setText(texts[2]);
        }
    }
}
