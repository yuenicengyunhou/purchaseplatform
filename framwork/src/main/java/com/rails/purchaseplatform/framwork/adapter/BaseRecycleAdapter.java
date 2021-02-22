package com.rails.purchaseplatform.framwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.ViewPositionListener;

import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


/**
 * author: wangchao
 * date:on 2017/5/12
 * Recyclerview  adapter
 */
public abstract class BaseRecycleAdapter<T, E extends ViewDataBinding> extends RecyclerView.Adapter {
    protected Context mContext;
    protected ArrayList<T> mDataSource;
    protected PositionListener positionListener;
    protected MulPositionListener mulPositionListener;
    protected ViewPositionListener viewPositionListener;

    public BaseRecycleAdapter(Context context) {
        this.mContext = context;
        mDataSource = new ArrayList<>();
    }

    protected abstract @LayoutRes
    int getContentID();

    protected abstract void onBindItem(E binding, T t, int position);


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        E bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), getContentID(), parent, false);
        return new RecyclerHolder(bing.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        E binding = DataBindingUtil.getBinding(holder.itemView);
        final T t = mDataSource.get(position);
        onBindItem(binding, t, position);
    }

    /**
     * 设置item点击接口
     *
     * @param positionListener
     */
    public void setListener(PositionListener positionListener) {
        this.positionListener = positionListener;
    }

    /**
     * 设置item点击接口
     *
     * @param viewPositionListener
     */
    public void setViewPositionListener(ViewPositionListener viewPositionListener) {
        this.viewPositionListener = viewPositionListener;
    }

    /**
     * 多参数接口
     *
     * @param mulPositionListener
     */
    public void setMulPositionListener(MulPositionListener mulPositionListener) {
        this.mulPositionListener = mulPositionListener;
    }

    /**
     * 更新数据
     *
     * @param itemDatas
     * @param isClear   是否刷新
     */
    public void update(ArrayList itemDatas, boolean isClear) {
        if (isClear)
            this.mDataSource.clear();
        if (itemDatas != null && !itemDatas.isEmpty()) {
            this.mDataSource.addAll(itemDatas);
        }
        this.notifyDataSetChanged();
    }


    /**
     * 删除item
     *
     * @param position item 所在位置
     */
    public void updateRemove(int position) {
        mDataSource.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        if (mDataSource.size() <= 0)
            notifyDataSetChanged();
    }

    /**
     * 添加 item
     *
     * @param item
     * @param position
     */
    public void updateAdd(T item, int position) {
        mDataSource.add(position, item);
        notifyItemInserted(position);
    }


    /**
     * 添加item
     *
     * @param item
     */
    public void updateAdd(T item) {
        mDataSource.add(item);
        notifyItemInserted(mDataSource.size() - 1);
    }


    /**
     * 添加item 全局刷新
     *
     * @param item
     * @param position
     */
    public void updateRefreshAdd(T item, int position) {
        mDataSource.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * 添加 局部范围刷新
     *
     * @param item
     * @param startPosition
     * @param size
     */
    public void updateRefreshRangeAdd(T item, int startPosition, int size) {
        mDataSource.add(startPosition, item);
        notifyItemRangeChanged(startPosition, size);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public int getCount() {
        return mDataSource.size();
    }


    static class RecyclerHolder extends RecyclerView.ViewHolder {
        public RecyclerHolder(View itemView) {
            super(itemView);
        }
    }

}