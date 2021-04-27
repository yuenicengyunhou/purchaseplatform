package com.rails.purchaseplatform.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;

import java.util.List;

public class OrderSearchFilterAdapter2 extends RecyclerView.Adapter {

    private Context mContext;
    private List<OrderStatusBean> mData;

    private PositionListener<OrderStatusBean> positionListener;


    public void setPositionListener(PositionListener<OrderStatusBean> positionListener) {
        this.positionListener = positionListener;
    }

    public OrderSearchFilterAdapter2(Context context, List<OrderStatusBean> list) {
        mContext = context;
        mData = list;
    }

    public List<OrderStatusBean> getmData() {
        return mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_order_status, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RelativeLayout relativeLayout = ((ViewHolder) holder).relativeLayout;
        CheckBox checkBox = ((ViewHolder) holder).textView;
        OrderStatusBean bean = mData.get(position);
        boolean checked = bean.isChecked();
        checkBox.setText(bean.getStatus());
        checkBox.setChecked(checked);
        checkBox.setOnClickListener(v -> {
            for (int i = 0; i < mData.size(); i++) {
                OrderStatusBean statusBean = mData.get(i);
                if (position == i) {
                    statusBean.setChecked(true);
                } else {
                    statusBean.setChecked(false);
                }
            }
            notifyDataSetChanged();
        });
        relativeLayout.setOnClickListener(v -> {
            if (null != positionListener) {
                positionListener.onPosition(bean, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        CheckBox textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rl_status);
            textView = itemView.findViewById(R.id.tv_status);
        }
    }
}
