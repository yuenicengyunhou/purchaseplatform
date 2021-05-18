package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.framwork.adapter.BaseAbsAdapter;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;
import com.rails.purchaseplatform.market.R;

public class SearchItemFilterSubAdapter extends BaseAbsAdapter<SearchFilterValue> {

    private Context mContext;

    private static int COUNTING = 1;
    private int COUNT_NUM = 60;


    private boolean isMultiSelect;

    public SearchItemFilterSubAdapter(Context context, boolean isMultiSelect) {
        super(context);
        this.mContext = context;
        this.isMultiSelect = isMultiSelect;
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
            LoadingDialog mLoadingDialog = new LoadingDialog.Builder(mContext).setMessage("Loading").create();
            mLoadingDialog.setCancelable(true);
            LoadingHandler mHandler = new LoadingHandler(mLoadingDialog);
            mLoadingDialog.show();

            if (!isMultiSelect) {
                for (SearchFilterValue value : mDataSource) {
                    if (value.isSelect()) {
                        value.setSelect(!value.isSelect());
                    }
                }
            }
            mDataSource.get(position).setSelect(!select);
            notifyDataSetChanged();

            Message message = new Message();
            message.what = COUNTING;
            message.obj = COUNT_NUM;
            mHandler.sendMessageDelayed(message, 500);
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
