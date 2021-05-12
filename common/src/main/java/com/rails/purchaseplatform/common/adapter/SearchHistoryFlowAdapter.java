package com.rails.purchaseplatform.common.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.common.R;

import java.util.List;

public class SearchHistoryFlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> mData;
    private OnClickCallBack callBack;
    private int mType;

    public SearchHistoryFlowAdapter(Context context, List<String> list) {
        mContext = context;
        mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_search_history, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = ((ViewHolder) holder).textView;
        String text = mData.get(position);
        textView.setText(text);
        textView.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putInt("search_type", mType);
            bundle.putString("search_key", text);
            bundle.putString("mode", "form_search");
            callBack.onClickCallBack(text, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_search_history);
        }
    }

    public void setSearchType(int searchType) {
        mType = searchType;
    }

    public void setListener(OnClickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnClickCallBack {
        void onClickCallBack(String text, Bundle bundle);
    }
}
