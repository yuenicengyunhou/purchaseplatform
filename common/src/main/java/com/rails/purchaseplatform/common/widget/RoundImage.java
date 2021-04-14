package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.rails.purchaseplatform.common.R;

import androidx.annotation.Nullable;

/**
 * 宽高比图片
 * Created by sk on 2017/4/21.
 */

public class RoundImage extends RoundImageView {

    int w = 1;
    int h = 1;

    public RoundImage(Context context) {
        super(context);
        init(context, null);
    }

    public RoundImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ratioImage);
        w = typedArray.getInt(R.styleable.ratioImage_w, 1);
        h = typedArray.getInt(R.styleable.ratioImage_h, 1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * h / w;
        setMeasuredDimension(width, height);
    }


    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }
}
