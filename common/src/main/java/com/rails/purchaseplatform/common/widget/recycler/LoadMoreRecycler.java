/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.rails.purchaseplatform.common.widget.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * 支持上拉加载更多的
 */
public class LoadMoreRecycler extends BaseRecyclerView {
    /**
     * item 类型
     */
    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_FOOTER = 1;//底部--往往是loading_more
    public final static int TYPE_LIST = 2;//代表item展示的模式是list模式
    public final static int TYPE_GRID = 3;//代码item展示模式是网格模式

    private boolean mIsFooterEnable = false;//是否允许加载更多

    /**
     * 自定义实现了头部和底部加载更多的adapter
     */
    private AutoLoadAdapter mAutoLoadAdapter;
    /**
     * 标记是否正在加载更多，防止再次调用加载更多接口
     */
    private boolean mIsLoadingMore;
    /**
     * 标记加载更多的position
     */
    private int mLoadMorePosition;
    /**
     * 加载更多的监听-业务需要实现加载数据
     */
    private LoadMoreListener mListener;

    public LoadMoreRecycler(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化-添加滚动监听
     * <p/>
     * 回调加载更多方法，前提是
     * <pre>
     *    1、有监听并且支持加载更多：null != mListener && mIsFooterEnable
     *    2、目前没有在加载，正在上拉（dy>0），当前最后一条可见的view是否是当前数据列表的最好一条--及加载更多
     * </pre>
     */
    private void init() {
        super.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != mListener && mIsFooterEnable && !mIsLoadingMore && (dy > 0 || dx > 0)) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition + 2 >= mAutoLoadAdapter.getItemCount()) {
                        setLoadingMore(true);
                        mLoadMorePosition = lastVisiblePosition;
                        mListener.onLoadMore();
                    }
                }
            }
        });
    }

    /**
     * 设置加载更多的监听
     *
     * @param listener
     */
    public void setLoadMoreListener(LoadMoreListener listener) {
        mListener = listener;
    }

    /**
     * 设置正在加载更多
     *
     * @param loadingMore
     */
    public void setLoadingMore(boolean loadingMore) {
        this.mIsLoadingMore = loadingMore;
    }

    /**
     * 加载更多监听
     */
    public interface LoadMoreListener {
        /**
         * 加载更多
         */
        void onLoadMore();
    }

    /**
     *
     */
    public class AutoLoadAdapter extends Adapter<ViewHolder> {

        /**
         * 数据adapter
         */
        private Adapter mInternalAdapter;


        public AutoLoadAdapter(Adapter adapter) {
            mInternalAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            int footerPosition = getItemCount() - 1;
            if (footerPosition == position && mIsFooterEnable) {
                return TYPE_FOOTER;
            } else {
                return mInternalAdapter.getItemViewType(position);
            }
            /**
             * 这么做保证layoutManager切换之后能及时的刷新上对的布局
             */
//            if (getLayoutManager() instanceof LinearLayoutManager && getLayoutManager() instanceof GridLayoutManager) {
//                return TYPE_GRID;
//            } else if (getLayoutManager() instanceof LinearLayoutManager) {
//                return TYPE_LIST;
//            } else {
//                return TYPE_NORMAL;
//            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                return new FooterViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.list_foot_loading, parent, false));
            } else { // type normal
                return mInternalAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        public class FooterViewHolder extends ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type != TYPE_FOOTER) {
                mInternalAdapter.onBindViewHolder(holder, position);
            }
        }


        /**
         * 需要计算上加载更多和添加的头部俩个
         *
         * @return
         */
        @Override
        public int getItemCount() {
            int count = mInternalAdapter.getItemCount();
            if (mIsFooterEnable) count++;

            return count;
        }


    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAutoLoadAdapter = new AutoLoadAdapter(adapter);
            adapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    mAutoLoadAdapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    mAutoLoadAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeRemoved(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeChanged(fromPosition, toPosition, itemCount);
                }
            });


        }
        super.swapAdapter(mAutoLoadAdapter, false);
    }

    /**
     * 切换layoutManager
     * <p>
     * 为了保证切换之后页面上还是停留在当前展示的位置，记录下切换之前的第一条展示位置，切换完成之后滚动到该位置
     * 另外切换之后必须要重新刷新下当前已经缓存的itemView，否则会出现布局错乱（俩种模式下的item布局不同），
     * RecyclerView提供了swapAdapter来进行切换adapter并清理老的itemView cache
     */
    public void switchLayoutManager(LayoutManager layoutManager) {
        int firstVisiblePosition = getFirstVisiblePosition();
        setLayoutManager(layoutManager);
        getLayoutManager().scrollToPosition(firstVisiblePosition);
    }


    /**
     * 获取第一条展示的位置
     *
     * @return
     */
    private int getFirstVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 2;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    /**
     * 设置是否支持自动加载更多
     *
     * @param autoLoadMore
     */
    public void setAutoLoadMoreEnable(boolean autoLoadMore) {
        mIsFooterEnable = autoLoadMore;
    }

    /**
     * 通知更多的数据已经加载
     * <p>
     * 每次加载完成之后添加了Data数据，用notifyItemRemoved来刷新列表展示，
     * 而不是用notifyDataSetChanged来刷新列表
     *
     * @param hasMore
     */
    public void notifyMoreFinish(boolean hasMore) {
        setAutoLoadMoreEnable(hasMore);
        try {
            getAdapter().notifyItemRemoved(mLoadMorePosition);
        } catch (Exception e) {
        }
        mIsLoadingMore = false;
    }

}
