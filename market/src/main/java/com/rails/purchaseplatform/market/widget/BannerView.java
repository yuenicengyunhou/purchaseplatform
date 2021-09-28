package com.rails.purchaseplatform.market.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;


import com.rails.purchaseplatform.common.widget.banner.Banner;

import androidx.annotation.Nullable;

/**
 * @author： sk_comic@163.com
 * @date: 2021/9/24
 */
public class BannerView  extends Banner {

    int w = 1;
    int h = 1;

    public BannerView(Context context) {
        super(context);
        init(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }



    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.rails.purchaseplatform.common.R.styleable.ratioImage);
        w = typedArray.getInt(com.rails.purchaseplatform.common.R.styleable.ratioImage_w, 1);
        h = typedArray.getInt(com.rails.purchaseplatform.common.R.styleable.ratioImage_h, 1);
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
