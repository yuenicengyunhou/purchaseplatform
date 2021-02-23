package com.rails.purchaseplatform.framwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * 多样式布局adapter，dataBinding封装
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public abstract class BaseMulRecyclerAdapter<T extends MulInterface> extends BaseRecyclerAdapter<T, ViewDataBinding> {


    public BaseMulRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSource.get(position).getContentID();
    }


    @NonNull
    @Override
    public BaseHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseHolder(DataBindingUtil.inflate(LayoutInflater.from(mContext), viewType, parent, false));

    }

}
