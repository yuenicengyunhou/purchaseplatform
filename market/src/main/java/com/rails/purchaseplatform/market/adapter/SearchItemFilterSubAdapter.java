package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.tags.OnInitSelectedPosition;
import com.rails.purchaseplatform.framwork.adapter.BaseAbsAdapter;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;
import com.rails.purchaseplatform.market.R;

import androidx.annotation.NonNull;

public class SearchItemFilterSubAdapter
        extends BaseAbsAdapter<SearchFilterValue>
        implements OnInitSelectedPosition {
    final private String TAG = SearchItemFilterSubAdapter.class.getSimpleName();

    private static int COUNTING = 1;
    private boolean isMultiSelect;
    private boolean isLastGroup;

    public SearchItemFilterSubAdapter(Context context, boolean isMultiSelect) {
        super(context);
        this.mContext = context;
        this.isMultiSelect = isMultiSelect;
    }

    public SearchItemFilterSubAdapter(Context context, boolean isMultiSelect, boolean isLastGroup) {
        super(context);
        this.mContext = context;
        this.isMultiSelect = isMultiSelect;
        this.isLastGroup = isLastGroup;
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
        mDataSource.get(position).setSelect(select);
        holder.rbTag.setSelected(select);
        holder.rbTag.setOnClickListener(v -> {
            if (!isMultiSelect) {
                for (SearchFilterValue value : mDataSource) {
                    if (value.isSelect()) {
                        value.setSelect(!value.isSelect());
                    }
                }
            }
            mDataSource.get(position).setSelect(!select);
            notifyDataSetChanged();
        });

        return convertView;
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return mDataSource.get(position).isSelect();
    }

    class ViewHolder {

        TextView rbTag;

        ViewHolder(View view) {
            rbTag = view.findViewById(R.id.rb_tag);
            rbTag.setGravity(Gravity.CENTER);
        }
    }


    private static class LoadingHandler extends Handler {
        private final LoadingDialog mDialog;

        public LoadingHandler(LoadingDialog dialog) {
            mDialog = dialog;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == COUNTING) {
                if (mDialog.isShowing())
                    mDialog.dismiss();
            }
        }
    }
}
