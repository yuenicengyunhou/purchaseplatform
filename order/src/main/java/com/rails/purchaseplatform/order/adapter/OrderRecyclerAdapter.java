package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderRecyclerBinding;

import java.util.ArrayList;

public class OrderRecyclerAdapter extends BaseRecyclerAdapter<OrderBean, ItemOrderRecyclerBinding> {
    private Context mContext;

//    private


    public OrderRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_recycler;
    }

    @Override
    protected void onBindItem(ItemOrderRecyclerBinding binding, OrderBean orderBean, int position) {
        binding.setOrderItemBean(orderBean);
        String orderNumber = orderBean.getOrderNumber();
        String generateTime = orderBean.getGenerateTime();
        String provider = orderBean.getProvider();
        String buyer = orderBean.getBuyer();
        String delayTime = orderBean.getDelayTime() == null ? "" : orderBean.getDelayTime();
        binding.t4ociTitle.setText(orderNumber, generateTime, provider, buyer, delayTime);
        binding.t4ociTitle.setOnClickListener(v -> Toast.makeText(mContext, "暂时没有跳转内容...", Toast.LENGTH_SHORT).show());


        ArrayList<OrderItemBean> orderItemBeans = (ArrayList<OrderItemBean>) orderBean.getOrderItemBeans();
        OrderChildRecyclerAdapter adapter = new OrderChildRecyclerAdapter(mContext);
        binding.brvChildOrderRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvChildOrderRecycler.setAdapter(adapter);
        adapter.update(orderItemBeans, true);
    }
}
