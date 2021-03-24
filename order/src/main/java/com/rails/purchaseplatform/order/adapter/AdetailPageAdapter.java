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

public class AdetailPageAdapter extends RecyclerView.Adapter<AdetailPageAdapter.ViewHolder> {
    Context context;

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }
    ArrayList<String> strings = new ArrayList();
    public AdetailPageAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_deliypage_one, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position==0) holder.viewLeft.setVisibility(View.GONE);
        if (position==strings.size()-1) holder.viewRight.setVisibility(View.GONE);

        if (position==1){
            holder.viewLeft.setBackgroundResource(R.color.purple_700);
            holder.imageView.setImageResource(R.drawable.ic_baseline_fiber_manual_blue);
        }
        if (position<1){
            holder.viewLeft.setBackgroundResource(R.color.purple_700);
            holder.viewRight.setBackgroundResource(R.color.purple_700);
            holder.imageView.setImageResource(R.drawable.ic_baseline_fiber_manual_blue);
        }
        holder.title.setText(strings.get(position));


    }

    @Override
    public int getItemCount() {
        return strings.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView imageView;
        private final View viewLeft;
        private final View viewRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.iv_item1);
            viewLeft = itemView.findViewById(R.id.view2);
            viewRight = itemView.findViewById(R.id.view3);

        }
    }
}
