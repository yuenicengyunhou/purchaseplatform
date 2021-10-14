package com.rails.purchaseplatform.market.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author： sk_comic@163.com
 * @date: 2021/9/27
 */
public class UnslideRecycler extends RecyclerView {
    public UnslideRecycler(@NonNull Context context) {
        super(context);
    }

    public UnslideRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UnslideRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
//        return super.onInterceptTouchEvent(e);
        return false;
    }
}
