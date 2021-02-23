package com.rails.purchaseplatform.framwork.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 定义viewdatabing的viewHolder
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class BaseHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected final T t;

    public BaseHolder(T t) {
        super(t.getRoot());
        this.t = t;
    }


    public T getBinding() {
        return t;
    }
}
