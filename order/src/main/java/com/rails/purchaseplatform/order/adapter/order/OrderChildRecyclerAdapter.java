package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.OrderItemBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.RatioImage;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.order.R;

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
            return new ItemHolder(layoutInflater.inflate(R.layout.item_order_child, parent, false));
        else
            return new ItemHolder(layoutInflater.inflate(R.layout.item_order_child_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        OrderItemBean bean = mDataSource.get(position);
        Glide.with(mContext).load(bean.getPictureUrl()).into(holder.imgProduct);
        holder.itemView.setOnClickListener(v -> {
//            ARouter.getInstance()
//                    .build(ConRoute.MARKET.PRODUCT_DETAIL)
//                    .navigation();
        });
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        RatioImage imgProduct;
        TextView tvTitle;
        TextView tvProperty;
        TextView tvCode;

        public ItemHolder(@NonNull View v) {
            super(v);
            imgProduct = v.findViewById(R.id.img_product);
            tvTitle = v.findViewById(R.id.tv_title);
            tvProperty = v.findViewById(R.id.tv_property);
            tvCode = v.findViewById(R.id.tv_code);

        }
    }

}
