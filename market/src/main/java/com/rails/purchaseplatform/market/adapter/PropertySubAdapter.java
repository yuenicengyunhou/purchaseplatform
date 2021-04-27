package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.purchaseplatform.framwork.adapter.BaseAbsAdapter;
import com.rails.purchaseplatform.market.R;

/**
 * 筛选条件楼层情况列表
 * author :Created by sk.
 * date : 2016/10/17.
 * email: wangchao@lepu.cn
 */

public class PropertySubAdapter extends BaseAbsAdapter<SpecificationValue> {


    public PropertySubAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_property_sub, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        holder.rbTag.setText(mDataSource.get(i).getAttrValueName());
        return view;
    }

    class ViewHolder {

        TextView rbTag;

        ViewHolder(View view) {
            rbTag = view.findViewById(R.id.rb_tag);
            rbTag.setGravity(Gravity.CENTER);
        }
    }

}
