package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class BaseViewPager extends ViewPager {

    private float xDistance = 0, yDistance = 0, lastX = 0, lastY = 0;

    public BaseViewPager(@NonNull Context context) {
        super(context);
    }

    public BaseViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                xDistance = yDistance = 0f;
//                lastX = ev.getX();
//                lastY = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float curX = ev.getX();
//                final float curY = ev.getY();
//                xDistance += Math.abs(curX - lastX);
//                yDistance += Math.abs(curY - lastY);
//                lastX = curX;
//                lastY = curY;
//                if (xDistance > yDistance)
//                    return false;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

}
