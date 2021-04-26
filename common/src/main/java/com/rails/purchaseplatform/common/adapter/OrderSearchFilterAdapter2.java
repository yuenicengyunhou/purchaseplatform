package com.rails.purchaseplatform.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.purchaseplatform.common.R;

import java.util.List;

public class OrderSearchFilterAdapter2 extends RecyclerView.Adapter {

    private Context mContext;
    private List<OrderStatusBean> mData;

    public OrderSearchFilterAdapter2(Context context, List<OrderStatusBean> list) {
        mContext = context;
        mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_order_status, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RelativeLayout relativeLayout = ((ViewHolder) holder).relativeLayout;
        TextView textView = ((ViewHolder) holder).textView;
        textView.setText(mData.get(position).getStatus());
        relativeLayout.setOnClickListener(v -> {
            Toast.makeText(mContext, mData.get(position).getStatusCode(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rl_status);
            textView = itemView.findViewById(R.id.tv_status);
        }
    }
}
