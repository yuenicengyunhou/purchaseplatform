package com.rails.purchaseplatform.common.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;


public abstract class ExpandFilterAdapter<T, E extends RecyclerView.ViewHolder> extends BaseRecycleAdapter<T, E> {

    private int TYPE_PARENT = 1;
    private int Type_CHILD = 2;

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public ExpandFilterAdapter(Context context) {
        super(context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return getItemViewType(position) == Type_CHILD? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
