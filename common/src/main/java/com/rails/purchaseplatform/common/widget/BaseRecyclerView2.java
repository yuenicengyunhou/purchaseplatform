package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.rails.purchaseplatform.common.R;

import java.util.Objects;

public class BaseRecyclerView2 extends RecyclerView{

    private int layoutMode;
    public static final int LIST = 1;
    public static final int GRID = 2;
    public static final int STAGGERED = 3;
    private RecyclerView.LayoutManager layoutManager;

    private boolean canVertical = true;
    private boolean canHorizonal = true;
    private View emptyView;
    private RecyclerView.AdapterDataObserver observer;
    private RecyclerView.Adapter mAdapter;

    private float xDistance, yDistance, lastX, lastY;

    private int mTouchSlop;
    private int mScrollPointerId;
    private int mInitialTouchX, mInitialTouchY;

    public BaseRecyclerView2(Context context) {
        super(context);
        init(context, null);
    }

    public BaseRecyclerView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseRecyclerView2(Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.getContext());
        switch (slopConstant) {
            case 0:
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                break;
            case 1:
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                break;
            default:
                Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + slopConstant + "; using default value"+"=======mTouchSlop---"+slopConstant);
                break;
        }
        super.setScrollingTouchSlop(slopConstant);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean canScrollHorizontally = Objects.requireNonNull(getLayoutManager()).canScrollHorizontally();
        boolean canScrollVertically = getLayoutManager().canScrollVertically();
        int action = e.getActionMasked();
        int actionIndex = e.getActionIndex();
        switch (action) {
            //ACTION_DOWN
            case 0:
                mScrollPointerId = e.getPointerId(0);
                this.mInitialTouchX = (int) (e.getX() + 0.5F);
                this.mInitialTouchY = (int) (e.getY() + 0.5F);
                return super.onInterceptTouchEvent(e);
            //ACTION_MOVE
            case 2:
                int index = e.findPointerIndex(this.mScrollPointerId);
                if (index < 0) {
                    Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                int x = (int) (e.getX(index) + 0.5F);
                int y = (int) (e.getY(index) + 0.5F);
                if (getScrollState() != 1) {
                    int dx = x - this.mInitialTouchX;
                    int dy = y - this.mInitialTouchY;
                    boolean startScroll = false;
                    if (canScrollHorizontally && Math.abs(dx) > this.mTouchSlop && Math.abs(dx) > Math.abs(dy)) {
                        startScroll = true;
                    }

                    if (canScrollVertically && Math.abs(dy) > this.mTouchSlop && Math.abs(dy) > Math.abs(dx)) {
                        startScroll = true;
                    }

                    Log.d("MyRecyclerView", "canX:" + canScrollHorizontally + "--canY" + canScrollVertically + "--dx:" + dx + "--dy:" + dy + "--startScorll:" + startScroll + "--mTouchSlop" + mTouchSlop);

                    return startScroll && super.onInterceptTouchEvent(e);
                }
                return super.onInterceptTouchEvent(e);
            //ACTION_POINTER_DOWN
            case 5:
                this.mScrollPointerId = e.getPointerId(actionIndex);
                this.mInitialTouchX = (int) (e.getX(actionIndex) + 0.5F);
                this.mInitialTouchY = (int) (e.getY(actionIndex) + 0.5F);
                return super.onInterceptTouchEvent(e);
        }

        return super.onInterceptTouchEvent(e);

    }

    @Override
    public void addOnItemTouchListener(@NonNull RecyclerView.OnItemTouchListener listener) {
        super.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
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
                                    if (xDistance < yDistance) {
                                        requestDisallowInterceptTouchEvent(canVertical);
                                    } else
                                        requestDisallowInterceptTouchEvent(canVertical);
                            }
                            return false;
                        }
                    });
                    requestDisallowInterceptTouchEvent(false);
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

    public void setCanVertical(boolean canVertical) {
        this.canVertical = canVertical;
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
                observer = new RecyclerView.AdapterDataObserver() {
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
    protected void refreshEmptyView(RecyclerView.Adapter adapter) {
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
