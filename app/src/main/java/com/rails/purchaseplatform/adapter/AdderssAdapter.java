package com.rails.purchaseplatform.adapter;

import android.content.Context;
import android.provider.DocumentsContract;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.serializer.AdderSerializer;
import com.rails.purchaseplatform.Bean.AddressBean;
import com.rails.purchaseplatform.R;

import java.util.List;

public class AdderssAdapter extends RecyclerView.Adapter {
    private List<AddressBean> mList;
    private Context context;

    public AdderssAdapter(List<AddressBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);
        ViewHolder1 holder1 = new ViewHolder1(inflate);
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressBean addressBean = mList.get(position);
        ViewHolder1 holder1= (ViewHolder1) holder;
        holder1.tv_name.setText(addressBean.getName());
        holder1.tv_phone.setText(addressBean.getPhone());
        holder1.tv_address.setText(addressBean.getAddress());



    }

    @Override
    public int getItemCount() {
        return mList.size();

    }
    public static
    class ViewHolder1 extends  RecyclerView.ViewHolder{
        public View itemView;
        public TextView tv_name;
        public TextView tv_phone;
        public TextView tv_address;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            this.itemView= itemView;
            this.tv_name=(TextView) itemView.findViewById(R.id.tv_name);
            this.tv_phone=(TextView)itemView.findViewById(R.id.tv_phone);
            this.tv_address=(TextView)itemView.findViewById(R.id.tv_address);

        }
    }

}
