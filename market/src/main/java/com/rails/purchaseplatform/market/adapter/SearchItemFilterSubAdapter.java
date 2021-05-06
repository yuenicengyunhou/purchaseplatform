package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.framwork.adapter.BaseAbsAdapter;
import com.rails.purchaseplatform.market.R;

public class SearchItemFilterSubAdapter extends BaseAbsAdapter<SearchFilterValue> {
    public SearchItemFilterSubAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_property_sub, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rbTag.setText(mDataSource.get(position).getValueName());

        boolean select = mDataSource.get(position).isSelect();
        holder.rbTag.setSelected(select);
        holder.rbTag.setOnClickListener(v -> {
            mDataSource.get(position).setSelect(!select);
            notifyDataSetChanged();
        });

        return convertView;
    }

    class ViewHolder {

        TextView rbTag;

        ViewHolder(View view) {
            rbTag = view.findViewById(R.id.rb_tag);
            rbTag.setGravity(Gravity.CENTER);
        }
    }
}
