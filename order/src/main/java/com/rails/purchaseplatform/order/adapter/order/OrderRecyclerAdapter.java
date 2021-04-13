package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.order.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 订单列表
 */
public class OrderRecyclerAdapter extends BaseRecycleAdapter<OrderBean, OrderRecyclerAdapter.ItemHolder> {

    private static final int TYPE_V = 0;
    private static final int TYPE_H = 1;


    public OrderRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        int len = mDataSource.get(position).getOrderItemBeans().size();
        if (len > 1) {
            return TYPE_H;
        } else {
            return TYPE_V;
        }
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == TYPE_H) {
//            return new ItemHolder(layoutInflater.inflate(R.layout.item_order_recycler_grid, parent, false));
//        } else {
//
//        }
        return new ItemHolder(layoutInflater.inflate(R.layout.item_order, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        int type = getItemViewType(position);
        OrderBean mulOrderBean = mDataSource.get(position);

        String orderNumber = mulOrderBean.getOrderNumber();
        String generateTime = mulOrderBean.getGenerateTime();
        String provider = mulOrderBean.getProvider();
        String buyer = mulOrderBean.getBuyer();
        String delayTime = mulOrderBean.getDelayTime() == null ? "" : mulOrderBean.getDelayTime();


        ArrayList<OrderItemBean> orderItemBeans = (ArrayList<OrderItemBean>) mulOrderBean.getOrderItemBeans();
        OrderChildRecyclerAdapter adapter = new OrderChildRecyclerAdapter(mContext, getItemViewType(position));
        holder.recycler.setLayoutManager(BaseRecyclerView.LIST,
                type == TYPE_V ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL, false, 1);
        holder.recycler.setAdapter(adapter);
        // TODO: 2021/4/13 count 需要设置文字 从bean中获取
        holder.count.setVisibility(type == TYPE_V ? View.VISIBLE : View.GONE);
        // TODO: 2021/4/13 singlePrice 需要设置文字 从bean中获取
        holder.singlePrice.setText(type == TYPE_V ? "456.20" : "共100件");
        adapter.update(orderItemBeans, true);
        holder.orderItem.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_ORDER_DETAIL)
                    .withString("url", ConRoute.WEB_URL.ORDER_SUB_DETAIL)
                    .navigation();
        });
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private BaseRecyclerView recycler;
        private LinearLayout orderItem;
        private TextView singlePrice;
        private TextView count;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            recycler = itemView.findViewById(R.id.recycler);
            orderItem = itemView.findViewById(R.id.ll_order);
            count = itemView.findViewById(R.id.tv_count);
            singlePrice = itemView.findViewById(R.id.tv_num);
        }
    }
}
