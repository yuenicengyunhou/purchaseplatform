package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.SubSkuDemandInfoBean;
import com.rails.lib_data.bean.SubOrderInfoBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.order.R;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 订单列表
 */
public class OrderRecyclerAdapter extends BaseRecycleAdapter<SubOrderInfoBean, OrderRecyclerAdapter.ItemHolder> {

    private static final int TYPE_SKU_DETAIL_MODE = 0;//商品为一件时，显示条目详情模式
    private static final int TYPE_SKU_PIC_GRID_MODE = 1;//商品一件以上时，显示grid视图模式
    private final boolean isDetail;

    public OrderRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
        isDetail = PrefrenceUtil.getInstance(context).getBoolean(ConShare.BUTTON_DETAIL, false);
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
        String orderNo = subOrderInfoBean.getOrderNo();
        String orderTime = subOrderInfoBean.getOrderTime();
        int orderStatus = subOrderInfoBean.getOrderStatus();
        String orderStatusView = subOrderInfoBean.getOrderStatusView();

        if (orderStatus == 25 || orderStatus == 30 || orderStatus == 40) {
            holder.tvOrderNum.setText(orderNo);
            holder.tvStatus.setText(orderStatusView);
            holder.ivNext.setVisibility(View.VISIBLE);
            holder.tvOrderTime.setText(orderTime);
            holder.tvOrderTime.setVisibility(View.VISIBLE);
            holder.tvNumKey.setText("订单号：");
            holder.tvTimeKey.setVisibility(View.VISIBLE);

        } else {
            holder.tvOrderNum.setText(orderTime);
            holder.tvStatus.setText(orderStatusView);
            holder.ivNext.setVisibility(View.INVISIBLE);
            holder.tvNumKey.setText("生成时间：");
            holder.tvTimeKey.setVisibility(View.GONE);
            holder.tvOrderTime.setVisibility(View.GONE);
        }


        OrderChildRecyclerAdapter adapter = new OrderChildRecyclerAdapter(mContext, getItemViewType(position));
        holder.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 1);
        holder.recycler.setAdapter(adapter);

        // TODO: 2021/4/13 count 需要设置文字 从bean中获取
        holder.count.setVisibility(type == TYPE_SKU_DETAIL_MODE ? View.VISIBLE : View.GONE);
        // TODO: 2021/4/13 singlePrice 需要设置文字 从bean中获取
        if (type == TYPE_SKU_DETAIL_MODE) {
            if (!subSkuDemandInfo.isEmpty()) {
                SubSkuDemandInfoBean subSkuDemandInfoBean = subSkuDemandInfo.get(0);
                String sellPrice = subSkuDemandInfoBean.getSellPrice();
                int skuNums = subSkuDemandInfoBean.getSkuNums();
                holder.singlePrice.setText(MessageFormat.format("¥{0}", sellPrice));
                holder.count.setText(MessageFormat.format("×{0}", skuNums));
                String totalPrice = subSkuDemandInfoBean.getTotalPrice();
                holder.tvPrice.setText(totalPrice);
            } else {
                holder.singlePrice.setText("");
                holder.tvPrice.setText("");
            }
        } else {
            int temp = 0;
            double tempPrice = 0;
            for (int i = 0; i < subSkuDemandInfo.size(); i++) {
                int skuNums = subSkuDemandInfo.get(i).getSkuNums();
                String totalPrice = subSkuDemandInfo.get(i).getTotalPrice();
                float price = Float.parseFloat(totalPrice);
                tempPrice = tempPrice + price;
                temp = temp + skuNums;
            }
            holder.singlePrice.setText(MessageFormat.format("共{0}件", temp));
            String formattedPrice = new DecimalFormat("0.00").format(tempPrice);
            holder.tvPrice.setText(formattedPrice);
        }
        adapter.update(subSkuDemandInfo, true);

        holder.orderItem.setOnClickListener(v -> {
            if (orderStatus == 25 || orderStatus == 30 || orderStatus == 40) {
                if (!isDetail) {
                    ToastUtil.showCenter(mContext, mContext.getResources().getString(R.string.common_author_null));
                    return;
                }
             /*   ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_ORDER_DETAIL)
                        .withString("url", ConRoute.WEB_URL.ORDER_SUB_DETAIL)
                        .withString("orderNo", String.valueOf(orderNo))
                        .navigation();*/
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", orderNo);
                ARouter.getInstance().build(ConRoute.ORDER.ORDER_DELIVER).with(bundle).navigation();
            } else {
                ToastUtil.showCenter(mContext, "此状态订单无订单详情");
            }
        });
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private final BaseRecyclerView recycler;
        private final ConstraintLayout orderItem;
        private final TextView singlePrice;
        private final TextView count;
        private final TextView tvPrice;
        private final TextView tvOrderNum;
        private final TextView tvOrderTime;
        private final TextView tvStatus;
        private final ImageView ivNext;
        private final TextView tvTimeKey;
        private final TextView tvNumKey;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            recycler = itemView.findViewById(R.id.recycler);
            orderItem = itemView.findViewById(R.id.ll_order);
            count = itemView.findViewById(R.id.tv_count);
            singlePrice = itemView.findViewById(R.id.tv_num);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvNumKey = itemView.findViewById(R.id.tv_orderNumKey);
            tvOrderNum = itemView.findViewById(R.id.tv_orderNumValue);
            tvOrderTime = itemView.findViewById(R.id.tv_TimeValue);
            tvTimeKey = itemView.findViewById(R.id.tv_timeKey);
            tvStatus = itemView.findViewById(R.id.tv_orderStatus);
            ivNext = itemView.findViewById(R.id.iv_next);
        }
    }
}
