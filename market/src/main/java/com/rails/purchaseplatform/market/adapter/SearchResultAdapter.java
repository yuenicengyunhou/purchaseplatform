package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rails.purchaseplatform.market.R;

import java.util.List;

public class SearchResultAdapter extends BaseAdapter {

    private Context mContext;
    private List mData;

    public SearchResultAdapter(Context context, List list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return 10;
//        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResultViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_search_result, null);
            holder = new SearchResultViewHolder();
            holder.itemImage = convertView.findViewById(R.id.iv_image);
            holder.itemImage.setOnClickListener(v -> goItemDetailsActivity());
            holder.itemImage = convertView.findViewById(R.id.iv_image);
            holder.itemImage.setOnClickListener(v -> goItemDetailsActivity());
            holder.itemImage = convertView.findViewById(R.id.iv_image);
            holder.itemImage.setOnClickListener(v -> goItemDetailsActivity());
            holder.itemImage = convertView.findViewById(R.id.iv_image);
            holder.itemImage.setOnClickListener(v -> goItemDetailsActivity());
            convertView.setTag(holder);
        } else {
            holder = (SearchResultViewHolder) convertView.getTag();
        }
        holder.itemImage.setImageResource(0);
        holder.itemName.setText("戴尔U2515H");
        holder.itemShop.setText("戴尔官方旗舰店");
        holder.itemPrice.setText("1000元");
        return convertView;
    }

    private void goShopActivity() {

    }

    private void goItemDetailsActivity() {

    }

    class SearchResultViewHolder {
        ImageView itemImage;
        TextView itemName, itemShop, itemPrice;
    }
}
