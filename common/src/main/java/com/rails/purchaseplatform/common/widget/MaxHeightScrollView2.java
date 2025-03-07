package com.rails.purchaseplatform.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.widget.NestedScrollView;

import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

/**
 * 解决viewpager和scrollView冲突, 顶部标题栏渐变效果
 * author :Created by sk.
 * date : 2016/10/8.
 */

public class MaxHeightScrollView2 extends NestedScrollView {

    private float xDistance, yDistance, lastX, lastY;
    private int maxHeight;

    private ScrollViewListener scrollViewListener = null;

    public MaxHeightScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        try {
//            maxHeight = ScreenSizeUtil.getScreenHeight(scanForActivity(getContext())) >> 1;
            maxHeight = ScreenSizeUtil.getScreenHeight(scanForActivity(getContext())) * 4 / 5 - ScreenSizeUtil.dp2px(getContext(), 300);
        } catch (Exception e) {
            maxHeight = 800 - ScreenSizeUtil.dp2px(getContext(), 300);
        }

        if (height >= maxHeight) {
            setMeasuredDimension(widthMeasureSpec, maxHeight);
        } else {
            setMeasuredDimension(widthMeasureSpec, height);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (xDistance > yDistance)
                    return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(MaxHeightScrollView2 scrollView, int x, int y, int oldx, int oldy);
    }


    private Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        return null;
    }

}
