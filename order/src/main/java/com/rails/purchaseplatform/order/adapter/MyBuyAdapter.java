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
import com.rails.purchaseplatform.order.bean.OrderBean;

import java.util.List;

public class MyBuyAdapter extends RecyclerView.Adapter {
    private List<OrderBean> datas;
    private Context context;
    private View view;

    public MyBuyAdapter(List<OrderBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderBean bean = datas.get(position);
        ViewHolder holder1 = (ViewHolder) holder;
        Glide.with(context).load(bean.getImg()).into(holder1.img);
        Glide.with(context).load(bean.getImg()).into(holder1.img2);
        holder1.shu.setText(bean.getDanHao());
        holder1.timeShu1.setText(bean.getTime());
        holder1.peopleRen.setText(bean.getAddress());
        holder1.orderShu.setText(bean.getDanHao());
        holder1.timeShu1.setText(bean.getTime());
        holder1.tv_title.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView shu;
        public ImageView img_right;
        public TextView timeShu;
        public TextView peopleRen;
        public TextView orderShu;
        public TextView sendGood;
        public TextView timeShu1;
        public ImageView img;
        public TextView tv_title;
        public TextView money;
        public TextView shuLiang;
        public TextView type;
        public TextView totle;
        public TextView orderShu2;
        public TextView sendGood2;
        public TextView time2;
        public TextView timeShu2;
        public ImageView img2;
        public TextView tv_title2;
        public TextView money2;
        public TextView shuLiang2;
        public TextView type2;
        public TextView totle2;
        public TextView zongMonmy;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.shu = (TextView) rootView.findViewById(R.id.shu);
            this.img_right = (ImageView) rootView.findViewById(R.id.img_right);
            this.timeShu = (TextView) rootView.findViewById(R.id.timeShu);
            this.peopleRen = (TextView) rootView.findViewById(R.id.peopleRen);
            this.orderShu = (TextView) rootView.findViewById(R.id.orderShu);
            this.sendGood = (TextView) rootView.findViewById(R.id.sendGood);
            this.timeShu1 = (TextView) rootView.findViewById(R.id.timeShu1);
            this.img = (ImageView) rootView.findViewById(R.id.img);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.money = (TextView) rootView.findViewById(R.id.money);
            this.shuLiang = (TextView) rootView.findViewById(R.id.shuLiang);
            this.type = (TextView) rootView.findViewById(R.id.type);
            this.totle = (TextView) rootView.findViewById(R.id.totle);
            this.orderShu2 = (TextView) rootView.findViewById(R.id.orderShu2);
            this.sendGood2 = (TextView) rootView.findViewById(R.id.sendGood2);
            this.time2 = (TextView) rootView.findViewById(R.id.time2);
            this.timeShu2 = (TextView) rootView.findViewById(R.id.timeShu2);
            this.img2 = (ImageView) rootView.findViewById(R.id.img2);
            this.tv_title2 = (TextView) rootView.findViewById(R.id.tv_title2);
            this.money2 = (TextView) rootView.findViewById(R.id.money2);
            this.shuLiang2 = (TextView) rootView.findViewById(R.id.shuLiang2);
            this.type2 = (TextView) rootView.findViewById(R.id.type2);
            this.totle2 = (TextView) rootView.findViewById(R.id.totle2);
            this.zongMonmy = (TextView) rootView.findViewById(R.id.zongMonmy);
        }

    }
}
