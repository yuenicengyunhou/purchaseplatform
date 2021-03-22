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

public class May2adapter  extends RecyclerView.Adapter<May2adapter.ViewHolder> {
    private List<PurchaseBean.Student> list;
    private Context context;

    public May2adapter(List<PurchaseBean.Student> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @Override
    public void onBindViewHolder(@NonNull May2adapter.ViewHolder holder, int position) {
        PurchaseBean.Student student = list.get(position);
        ViewHolder holder1=(ViewHolder) holder;
        Glide.with(context).load(student.img).into(holder1.img);
        holder1.money.setText(student.money);
        holder1.orderShu.setText(student.count);
        holder1.sendGood.setText("已发货");
        holder1.shuLiang.setText("数量");
        holder1.timeShu1.setText("时间");
        holder1.totle.setText(student.count);
        holder1.type.setText("类型");
    }

    @NonNull
    @Override
    public May2adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recycler_item2, parent, false);
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
