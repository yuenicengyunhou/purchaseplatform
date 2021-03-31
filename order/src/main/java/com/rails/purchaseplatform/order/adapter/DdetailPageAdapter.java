package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.order.R;

import java.util.ArrayList;
import java.util.HashSet;

@Deprecated
public class DdetailPageAdapter extends RecyclerView.Adapter<DdetailPageAdapter.ViewHolder> {
    Context context;
    StringBuffer stringBuffer = new StringBuffer();
    ArrayList<String> strings = new ArrayList();
    HashSet<Integer> integers = new HashSet<>();

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();

    }

    public DdetailPageAdapter(Context context) {
        this.context = context;

    }


     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View inflate = LayoutInflater.from(context).inflate(R.layout.layout_detailpage_d, parent, false);
         return new ViewHolder(inflate);
     }

     @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_five.setText(strings.get(position));
        holder.tv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuffer.delete(0, stringBuffer.length());
                if (holder.imageViewSeven.getVisibility() == View.GONE) {
                    holder.imageViewSeven.setVisibility(View.VISIBLE);
                    integers.add((Integer) position);
                } else {
                    holder.imageViewSeven.setVisibility(View.GONE);
                    integers.remove((Integer) position);
                }
                for (Integer integer : integers) {
                    stringBuffer.append(integer);
                }
                Log.d("asd","onClick:  "+stringBuffer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tv_five;
        private final ImageView imageViewSeven;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_five = itemView.findViewById(R.id.danHao);
            imageViewSeven = itemView.findViewById(R.id.imageViewSeven);
        }
    }
}
