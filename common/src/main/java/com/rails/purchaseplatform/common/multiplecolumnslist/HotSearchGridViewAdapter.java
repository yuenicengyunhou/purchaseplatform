package com.rails.purchaseplatform.common.multiplecolumnslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.R;

import java.util.List;

public class HotSearchGridViewAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mDataList;

    public HotSearchGridViewAdapter(Context context, List<T> list) {
        mContext = context;
        mDataList = list;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hot_search, null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_hotSearchContent);
            holder.textView.setOnClickListener(v -> {
                // TODO: 2021/3/15 需要考虑传递数据，不同的搜索内容对应不同的搜索结果
                ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).navigation();
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText((String) mDataList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textView;

    }
}
