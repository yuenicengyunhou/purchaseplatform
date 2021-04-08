package com.rails.purchaseplatform.common.widget.recycler;

import android.content.Context;
import android.util.AttributeSet;

import com.rails.purchaseplatform.common.widget.BaseRecyclerView;

import androidx.annotation.Nullable;

/**
 * 跑马灯recycler
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class MarqueeRecycler extends BaseRecyclerView {
    public MarqueeRecycler(Context context) {
        super(context);
    }

    public MarqueeRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
