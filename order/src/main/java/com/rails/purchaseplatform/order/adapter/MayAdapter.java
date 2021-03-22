package com.rails.purchaseplatform.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.order.DeliypageActivity;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.bean.PurchaseBean;

import java.util.ArrayList;

public class MayAdapter extends RecyclerView.Adapter<MayAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PurchaseBean> list;
    public May2adapter may2adapter;

    public MayAdapter(Context context, ArrayList<PurchaseBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item1,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MayAdapter.ViewHolder holder, int position) {
        PurchaseBean purchaseBean = list.get(position);
      ViewHolder holder1 = (ViewHolder) holder;
       holder1.shu.setText(purchaseBean.danHao);
        holder1.timeShu.setText(purchaseBean.time);
        holder1.peopleRen.setText(purchaseBean.address);
        holder1.recyclerViewchid.setLayoutManager(new LinearLayoutManager(context));
        may2adapter = new May2adapter(purchaseBean.list,context);
        holder1.recyclerViewchid.setAdapter(may2adapter);
        holder1.img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeliypageActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static
    class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public TextView shu;
        public TextView timeShu;
        public TextView peopleRen;
        public ImageView img_right;
        public RecyclerView recyclerViewchid;
        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
             shu = rootView.findViewById(R.id.shu);
            timeShu = rootView.findViewById(R.id.timeShu);
            peopleRen = rootView.findViewById(R.id.peopleRen);
            img_right = rootView.findViewById(R.id.img_right);
            recyclerViewchid = rootView.findViewById(R.id.recyclerViewchid);

        }
    }
}
