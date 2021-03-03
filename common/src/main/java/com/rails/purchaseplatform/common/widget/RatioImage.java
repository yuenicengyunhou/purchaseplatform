package com.rails.purchaseplatform.common.widget;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;

/**
 * 宽高比图片
 * Created by sk on 2017/4/21.
 */

public class RatioImage extends androidx.appcompat.widget.AppCompatImageView {

    int ratio = 1;

    public RatioImage(Context context) {
        super(context);
    }

    public RatioImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * ratio;
        setMeasuredDimension(width, height);
    }
}
