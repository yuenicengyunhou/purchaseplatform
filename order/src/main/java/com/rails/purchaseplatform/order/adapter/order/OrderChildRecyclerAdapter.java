package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.framwork.adapter.BaseHolder;
import com.rails.purchaseplatform.framwork.adapter.BaseMulRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemChildOrderRecyclerBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class OrderChildRecyclerAdapter extends BaseRecycleAdapter<OrderItemBean, OrderChildRecyclerAdapter.ItemHolder> {

    private static final int TYPE_V = 0;
    private static final int TYPE_H = 1;
    private int type;

    public OrderChildRecyclerAdapter(Context context, int type) {
        super(context);
        mContext = context;
        this.type = type;
    }


    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_V)
            return new ItemHolder(layoutInflater.inflate(R.layout.item_child_order_recycler, parent, false));
        else
            return new ItemHolder(layoutInflater.inflate(R.layout.item_child_order_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
//        if (binding instanceof ItemChildOrderRecyclerBinding) {
//            ((ItemChildOrderRecyclerBinding) binding).setChildOrder(mulOrderItemBean);
//            double price = Double.parseDouble(mulOrderItemBean.getItemPrice());
//            int count = Integer.parseInt(mulOrderItemBean.getItemCount());
//            ((ItemChildOrderRecyclerBinding) binding).tvPriceValue.setText(String.valueOf(price * count));
//        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
