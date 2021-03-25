package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.bean.PurchaseBean;

import java.util.List;

public class OrderListsAdapter extends RecyclerView.Adapter<OrderListsAdapter.ViewHolder> {
    private List<PurchaseBean.Student> list;
    private Context context;

    public OrderListsAdapter(List<PurchaseBean.Student> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @Override
    public void onBindViewHolder(@NonNull OrderListsAdapter.ViewHolder holder, int position) {
        PurchaseBean.Student student = list.get(position);
        ViewHolder holder1=(ViewHolder) holder;
        Glide.with(context).load(student.img).into(holder1.img);
        holder1.money.setText(student.money);
        holder1.orderShu.setText(student.count);
        holder1.sendGood.setText("已发货");
        holder1.shuLiang.setText("x 10");
        holder1.timeShu1.setText("2021-01-11 17:29:30");
        holder1.totle.setText(student.total);
        holder1.type.setText("型号: 100支/盒");
    }

    @NonNull
    @Override
    public OrderListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recycler_detailpage_b, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView orderShu;
        public TextView timeShu1;
        public ImageView img;
        public TextView type;
        public TextView sendGood;
        public TextView money;
        public TextView shuLiang;
        public TextView totle;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            orderShu = rootView.findViewById(R.id.orderShu);
            timeShu1 = rootView.findViewById(R.id.timeShu1);
            img = rootView.findViewById(R.id.img);
            type = rootView.findViewById(R.id.type);
            sendGood = rootView.findViewById(R.id.sendGood);
            money = rootView.findViewById(R.id.money);
            shuLiang = rootView.findViewById(R.id.shuLiang);
            totle = rootView.findViewById(R.id.totle);
        }
    }
}
