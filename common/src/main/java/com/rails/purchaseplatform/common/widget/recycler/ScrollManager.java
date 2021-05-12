package com.rails.purchaseplatform.common.widget.recycler;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 可滚动布局器
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/12
 */
public class ScrollManager extends LinearLayoutManager {
    public ScrollManager(Context context) {
        super(context);
    }

    public ScrollManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScrollManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
