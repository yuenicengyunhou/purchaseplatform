package com.rails.purchaseplatform.market.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/4
 */
public class CenterManger extends LinearLayoutManager {
    public CenterManger(Context context) {
        super(context);
    }

    public CenterManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenterManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }


    private static class CenterSmoothScroller extends LinearSmoothScroller {

        private static final float MILLISECONDS_PER_INCH = 100f;

        public CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
//            return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }


        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
        }
    }
}
