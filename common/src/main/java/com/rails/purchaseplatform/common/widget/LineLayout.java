package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.common.R;


/**
 * 个人中心的view
 * author: wangchao {sk_comic@163.com}
 * date:on 2017/6/20
 */

public class LineLayout extends LinearLayout {

    private int mWidth, mHeight;
    private Paint mPaint, lPaint;
    private float radius;

    private int fillColor;
    private int startAngle;
    private int sweepAngle;
    private int calculateAngle;
    private int top, left;


    public LineLayout(Context context) {
        super(context);
        init(context, null);
    }

    public LineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    /**
     * 初始化view
     */
    private void init(Context context, AttributeSet attrs) {

        initTypeArray(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(fillColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 初始化样式
     *
     * @param context
     * @param attrs
     */
    private void initTypeArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineLayout);
        fillColor = typedArray.getColor(R.styleable.LineLayout_fill_color, Color.RED);
        startAngle = typedArray.getInteger(R.styleable.LineLayout_start_angle, 0);
        sweepAngle = typedArray.getInteger(R.styleable.LineLayout_sweep_angle, 0);
        calculateAngle = typedArray.getInteger(R.styleable.LineLayout_cal_angle, 30);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        top = 0;
        left = 0;
        mWidth = r - l;
        mHeight = b - t;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        double radian = Math.PI / 180 * calculateAngle;
        radius = (float) (mWidth / 2 / Math.sin(radian));
        float cosCalculate = (float) Math.cos(radian);
        float disHeight = radius - radius * cosCalculate;
        drawFillPath(canvas, disHeight);
    }


    /**
     * 填充线路
     *
     * @param canvas
     * @param disHeight
     */
    private void drawFillPath(Canvas canvas, float disHeight) {
        Path path = new Path();
        path.moveTo(mWidth, top);
        RectF rectF = new RectF(left, top, mWidth, disHeight);
        path.addArc(rectF, startAngle, sweepAngle);
        path.lineTo(left, top);
        path.lineTo(left, mHeight);
        path.lineTo(mWidth, mHeight);
        path.lineTo(mWidth, top);
        path.close();
        canvas.drawPath(path, mPaint);

    }
}