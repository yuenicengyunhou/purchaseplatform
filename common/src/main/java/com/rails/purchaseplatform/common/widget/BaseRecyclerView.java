package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.rails.purchaseplatform.common.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 复写recyclerView，简化activity代码量
 * Created by sk on 2016/7/29.
 */
public class BaseRecyclerView extends RecyclerView {

    private int layoutMode;
    public static final int LIST = 1;
    public static final int GRID = 2;
    public static final int STAGGERED = 3;
    private LayoutManager layoutManager;

    private boolean canVertical = true;
    private boolean canHorizonal = true;
    private View emptyView;
    private AdapterDataObserver observer;
    private Adapter mAdapter;

    private float xDistance, yDistance, lastX, lastY;

    public BaseRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    /**
     * 为自定义recycler设置属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.recycler);
        canVertical = typedArray.getBoolean(R.styleable.recycler_canV, true);
        canHorizonal = typedArray.getBoolean(R.styleable.recycler_canH, true);
        typedArray.recycle();
    }

    @Override
    public void addOnItemTouchListener(@NonNull OnItemTouchListener listener) {
        super.addOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                if (motionEvent != null) {
                    View view = findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (view != null) {
                        view.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent ev) {
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
                                        if (xDistance > yDistance) {
                                            requestDisallowInterceptTouchEvent(true);
                                        } else
                                            requestDisallowInterceptTouchEvent(false);
                                }
                                return false;
                            }
                        });
                        requestDisallowInterceptTouchEvent(false);
                    }

                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });


    }


    /**
     * 设置RecyclerView 的 layout 模式
     *
     * @param layoutMode
     */

    /**
     * 设置 RecyclerView 的layout 模式
     *
     * @param layoutMode    选择当前layout 模式
     * @param orientation   recyclerView的方向
     * @param reverseLayout recyclerView 一个方向数值，当true，layout form end to start
     * @param spanCount     如果选择gradview形式的列表结构，需要提供显示的列，别的没有作用
     */
    public void setLayoutManager(int layoutMode, int orientation, boolean reverseLayout, int spanCount) {
        switch (layoutMode) {
            case LIST:
                layoutManager = new LinearLayoutManager(getContext(), orientation, reverseLayout) {
                    @Override
                    public boolean canScrollVertically() {
                        return canVertical;
                    }

                    @Override
                    public boolean canScrollHorizontally() {
                        return canHorizonal;
                    }
                };
                break;
            case GRID:
                layoutManager = new GridLayoutManager(getContext(), spanCount, orientation, reverseLayout) {
                    @Override
                    public boolean canScrollVertically() {
                        return canVertical;
                    }

                    @Override
                    public boolean canScrollHorizontally() {
                        return canHorizonal;
                    }
                };
                break;
            case STAGGERED:
                layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL) {
                    @Override
                    public boolean canScrollVertically() {
                        return canVertical;
                    }

                    @Override
                    public boolean canScrollHorizontally() {
                        return canHorizonal;
                    }
                };
                break;
        }
        setLayoutManager(layoutManager);
    }


    /**
     * 禁止垂直滑动
     */
    public void canScrollVertical(boolean canVertical) {
        this.canVertical = canVertical;
    }


    /**
     * 禁止水平滑动
     *
     * @param canHorizonal
     */
    public void setCanHorizonal(boolean canHorizonal) {
        this.canHorizonal = canHorizonal;
    }


    /**
     * 添加View
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        if (emptyView == null)
            return;
        this.emptyView = emptyView;
        this.emptyView.setVisibility(View.GONE);
        mAdapter = getAdapter();
        if (mAdapter != null) {
            if (observer == null) {
                observer = new AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        refreshEmptyView(mAdapter);
                    }

                };
            }
            mAdapter.registerAdapterDataObserver(observer);
        }
    }


    /**
     * 当前RecyclerView 是否有数据
     */
    protected void refreshEmptyView(Adapter adapter) {
        if (emptyView != null) {
            int len = adapter.getItemCount();
            emptyView.setVisibility(len == 0 ? View.VISIBLE : View.GONE);
        }
    }


    /**
     * 解绑observer
     */
    public void detachedObserver() {
        if (mAdapter != null && observer != null) {
            mAdapter.unregisterAdapterDataObserver(observer);
        }
        mAdapter = null;
        observer = null;
    }

}
