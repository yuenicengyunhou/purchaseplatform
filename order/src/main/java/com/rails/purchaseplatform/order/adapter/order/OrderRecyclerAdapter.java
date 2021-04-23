package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.SubSkuDemandInfoBean;
import com.rails.lib_data.bean.SubOrderInfoBean;
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
public class OrderRecyclerAdapter extends BaseRecycleAdapter<SubOrderInfoBean, OrderRecyclerAdapter.ItemHolder> {

    private static final int TYPE_SKU_DETAIL_MODE = 0;//商品为一件时，显示条目详情模式
    private static final int TYPE_SKU_PIC_GRID_MODE = 1;//商品一件以上时，显示grid视图模式


    public OrderRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        int len = mDataSource.get(position).getSubSkuDemandInfo().size();
        if (len > 1) {
            return TYPE_SKU_PIC_GRID_MODE;
        } else {
            return TYPE_SKU_DETAIL_MODE;
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
        SubOrderInfoBean subOrderInfoBean = mDataSource.get(position);
        ArrayList<SubSkuDemandInfoBean> subSkuDemandInfo = subOrderInfoBean.getSubSkuDemandInfo();

        String orderNumber = subOrderInfoBean.getSkuNum();
        String generateTime = subOrderInfoBean.getOrderTime();
//        String provider = subOrderInfoBean.getProvider();
//        String buyer = subOrderInfoBean.getBuyer();
//        String delayTime = subOrderInfoBean.getDelayTime() == null ? "" : subOrderInfoBean.getDelayTime();


//        ArrayList<OrderItemBean> orderItemBeans = (ArrayList<OrderItemBean>) subOrderInfoBean.getOrderItemBeans();
        OrderChildRecyclerAdapter adapter = new OrderChildRecyclerAdapter(mContext, getItemViewType(position));
        holder.recycler.setLayoutManager(BaseRecyclerView.LIST,
                type == TYPE_SKU_DETAIL_MODE ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL, false, 1);
        holder.recycler.setAdapter(adapter);
        // TODO: 2021/4/13 count 需要设置文字 从bean中获取
        holder.count.setVisibility(type == TYPE_SKU_DETAIL_MODE ? View.VISIBLE : View.GONE);
        // TODO: 2021/4/13 singlePrice 需要设置文字 从bean中获取
        holder.singlePrice.setText(type == TYPE_SKU_DETAIL_MODE ? "456.20" : "共100件");
        adapter.update(subSkuDemandInfo, true);
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
