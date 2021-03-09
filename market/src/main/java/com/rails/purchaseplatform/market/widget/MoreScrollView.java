package com.rails.purchaseplatform.market.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import androidx.core.widget.NestedScrollView;


/**
 * author :Created by sk.
 * date : 2016/10/9.
 * email: wangchao@lepu.cn
 */

public class MoreScrollView extends NestedScrollView {
    private static final int ANIM_TIME = 300;
    private static final float MOVE_FACTOR = 0.5f;
    private int DEFHEIGHT;

    private int downY;
    private int delY;
    private View view;
    private boolean isMoved = false;
    private OnMoreListener moreListener;
    private Rect originalRect = new Rect();

    public MoreScrollView(Context context) {
        super(context);
    }

    public MoreScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DEFHEIGHT = ScreenSizeUtil.dp2px(context, 50);
    }

    public MoreScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMoreListener(OnMoreListener moreListener) {
        this.moreListener = moreListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            view = getChildAt(0);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (view == null)
            return;
        originalRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */

    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (view == null) {
            return super.dispatchTouchEvent(ev);
        }
        boolean isTouchOutOfScrollView = ev.getY() >= this.getHeight();
        if (isTouchOutOfScrollView) {
            if (isMoved)
                boundBack();
            return true;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) (nowY - downY);
                int offset = (int) (deltaY * MOVE_FACTOR);
                if (isMore() && offset < 0) {
                    view.layout(originalRect.left, originalRect.top + offset, originalRect.right, originalRect.bottom + offset);
                    delY = offset;
                }
                isMoved = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isMore() && Math.abs(delY) > DEFHEIGHT && delY < 0) {
                    if (moreListener != null) {
                        moreListener.onMore();
                    }
                }
                boundBack();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    private void boundBack() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, view.getTop(), originalRect.top);
        anim.setDuration(ANIM_TIME);
        view.startAnimation(anim);
        view.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
        isMoved = false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);
        if (moreListener != null) {
            moreListener.onChange(l, t, oldl, oldt);
        }
    }


    /**
     * 是否加载很多
     *
     * @return
     */
    private boolean isMore() {
        if (getScaleY() == 0)
            return false;
        return view.getMeasuredHeight() <= getHeight() + getScrollY();
    }

    public interface OnMoreListener {
        void onMore();

        void onChange(int l, int t, int oldl, int oldt);
    }
}
