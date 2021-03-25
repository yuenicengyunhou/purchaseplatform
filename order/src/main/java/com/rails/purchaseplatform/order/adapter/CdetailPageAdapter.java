package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.order.R;

import java.util.ArrayList;

public class CdetailPageAdapter extends RecyclerView.Adapter<CdetailPageAdapter.ViewHolder> {
    Context context;

    public CdetailPageAdapter(Context context) {
        this.context = context;
    }

    ArrayList<String> strings = new ArrayList();

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_detailpage_c, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) holder.view5.setVisibility(View.GONE);
        if (position == strings.size() - 1) holder.view6.setVisibility(View.GONE);

        if (position == 1) {
            holder.view5.setBackgroundResource(R.color.purple_700);
            holder.imageView.setImageResource(R.drawable.ic_baseline_fiber_manual_blue);
        }
        if (position < strings.size()) {
            holder.view5.setBackgroundResource(R.color.purple_700);
            holder.view6.setBackgroundResource(R.color.purple_700);
            holder.imageView.setImageResource(R.drawable.ic_baseline_fiber_manual_blue);
        }
        holder.tv_two.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView tv_two;
        private final View view5;
        private final View view6;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_two = itemView.findViewById(R.id.tv_two);
            imageView = itemView.findViewById(R.id.imageViewsix);
            view5 = itemView.findViewById(R.id.view5);
            view6 = itemView.findViewById(R.id.view6);

        }
    }
}
