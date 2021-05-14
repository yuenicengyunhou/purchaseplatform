package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rails.lib_data.SubSkuDemandInfoBean;
import com.rails.purchaseplatform.common.widget.RatioImage;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.order.R;

import java.text.MessageFormat;

public class OrderChildRecyclerAdapter extends BaseRecycleAdapter<SubSkuDemandInfoBean, OrderChildRecyclerAdapter.ItemHolder> {

    private static final int TYPE_SKU_DETAIL_MODE = 0;
    private static final int TYPE_SKU_PIC_GRID_MODE = 1;
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
        if (viewType == TYPE_SKU_DETAIL_MODE) {
            return new ItemHolder(layoutInflater.inflate(R.layout.item_order_child, parent, false));
        } else {
            return new ItemHolder(layoutInflater.inflate(R.layout.item_order_child_grid, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SubSkuDemandInfoBean bean = mDataSource.get(position);
        String sellPrice = bean.getSellPrice();
        Glide.with(mContext).load("https:" + bean.getPictureUrl()).into(holder.imgProduct);
        holder.tvTitle.setText(bean.getItemName());
//        holder.tvProperty.setText(MessageFormat.format("{0}{1}{2}", bean.getFirstName(), bean.getTwoName(), bean.getThreeName()));
        holder.tvProperty.setText(bean.getAttributes());
        holder.tvCode.setText(bean.getShortCode());
//        bean.getItemType()
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
