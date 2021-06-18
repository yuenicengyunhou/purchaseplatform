package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import org.jetbrains.annotations.NotNull;

public class ProgressButton  extends AppCompatButton {

    private int mTextColor;
    private int mProgressColor;
    private int mBorderColor;
    private int mBackgroundColor;
    private float mTextSize;

    private int mBorderWidth;

    private int mProgress;
    private Paint mBackgroundPaint;
    private Paint mTextPaint;

    public ProgressButton(@NonNull @NotNull Context context) {
        this(context,null,0);
    }

    public ProgressButton(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressButton(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        //解决文字有时候画不出问题
//        setLayerType(LAYER_TYPE_SOFTWARE, mTextPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void drawText() {

    }
}
