package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * 1 - 通过监视NestedScrollView滑动距离来计算TabLayout透明度和标签切换
 * 2 - 点击TabLayout标签时，使NestedScrollView滚动到对应的位置
 */
public class TabObserveNestedScrollView extends NestedScrollView {
    final private String TAG = TabObserveNestedScrollView.class.getSimpleName();

    public TabObserveNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public TabObserveNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabObserveNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
        Log.d(TAG, String.valueOf(target.getId()));
        Log.d(TAG, String.valueOf(target.getMeasuredHeight()));
        Log.d(TAG, "dxConsumed =====" + dxConsumed);
        Log.d(TAG, "dyConsumed =====" + dyConsumed);
        Log.d(TAG, "dxUnconsumed ===" + dxUnconsumed);
        Log.d(TAG, "dyUnconsumed ===" + dyUnconsumed);
        Log.d(TAG, "type ===========" + type);
    }
}
