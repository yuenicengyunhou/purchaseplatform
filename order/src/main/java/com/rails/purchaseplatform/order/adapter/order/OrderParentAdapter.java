package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.bean.OrderItemBean;
import com.rails.lib_data.bean.OrderParentBean;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.order.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 采购单列表
 */
public class OrderParentAdapter extends BaseRecycleAdapter<OrderParentBean, OrderParentAdapter.ItemHolder> {


    public OrderParentAdapter(Context context) {
        super(context);
        mContext = context;
    }


    @NonNull
    @Override
    public OrderParentAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(layoutInflater.inflate(R.layout.item_order_parent, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        OrderParentBean parentBean = mDataSource.get(position);

        ArrayList<OrderBean> orderItemBeans = (ArrayList<OrderBean>) parentBean.getOrder();
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(mContext);
        holder.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        holder.recycler.setAdapter(adapter);
        adapter.update(orderItemBeans, true);
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private BaseRecyclerView recycler;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            recycler = itemView.findViewById(R.id.recycler);
            recycler.addItemDecoration(new SpaceItemDecoration(1));

        }
    }
}
