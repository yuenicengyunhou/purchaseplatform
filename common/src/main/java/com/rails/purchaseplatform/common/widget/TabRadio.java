package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.rails.purchaseplatform.common.R;

import androidx.appcompat.widget.AppCompatRadioButton;


/**
 * 首页底部红点RadioButton
 * author: wangchao
 * date:on 2017/8/14
 */

public class TabRadio extends AppCompatRadioButton {

    private DisplayMetrics displayMestics;
    private int mWidth, mHeight;
    private Paint bgPaint, fontPaint;
    private Rect rect;

    private String number = "";
    private int txtPadding;
    private int distance;
    private int fontSize;
    private int fontColor;
    private boolean isHidetxt;//是否隐藏文本
    private boolean isHide;//是否整体隐藏
    private Point centerPoint, fontPoint;

    public TabRadio(Context context) {
        super(context);
        init(context, null);
    }

    public TabRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TabRadio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        mHeight = bottom - top;
        setCricleRect(number);
    }


    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabRadio);
        txtPadding = typedArray.getDimensionPixelSize(R.styleable.TabRadio_font_padding, 0);
        distance = typedArray.getDimensionPixelSize(R.styleable.TabRadio_distance, 0);
        fontSize = typedArray.getDimensionPixelSize(R.styleable.TabRadio_font_size, 10);
        isHidetxt = typedArray.getBoolean(R.styleable.TabRadio_is_hide_txt, false);
        isHide = typedArray.getBoolean(R.styleable.TabRadio_is_hide, true);
        fontColor = typedArray.getColor(R.styleable.TabRadio_font_color, Color.RED);
        typedArray.recycle();

        fontPoint = new Point();
        centerPoint = new Point();
        rect = new Rect();
        displayMestics = new DisplayMetrics();

        //背景颜色画笔
        bgPaint = new Paint();
        bgPaint.setColor(fontColor);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //字体颜色画笔
        fontPaint = new Paint();
        fontPaint.setTextSize(fontSize);
        fontPaint.setTextAlign(Paint.Align.CENTER);
        fontPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTab(canvas);
    }


    /**
     * 绘制红点
     *
     * @param canvas
     */
    private void drawTab(Canvas canvas) {
        if (TextUtils.isEmpty(number))
            return;
        canvas.drawRoundRect(new RectF(rect),30,30,bgPaint);
//        canvas.drawOval(new RectF(rect), bgPaint);
        if (!isHidetxt)
            canvas.drawText(number, fontPoint.x, fontPoint.y, fontPaint);

    }

    /**
     * 设置数字
     *
     * @param tabNumber
     */
    public void setNumber(int tabNumber) {
        if (tabNumber <= 0) {
            number = "";
        } else if (tabNumber > 99) {
            number = "99+";
        } else if (tabNumber > 0 && tabNumber <= 99) {
            number = String.valueOf(tabNumber);
        }
        requestLayout();
    }


    /**
     * 获取当前数值
     *
     * @return
     */
    public String getNumber() {
        return number;
    }


    /**
     * 为字符串配备相同大小的背景大小
     *
     * @param number
     */
    private void setCricleRect(String number) {
        //获取字符串的宽和高
        if (TextUtils.isEmpty(number))
            return;
        if (!isHidetxt) {
            Paint.FontMetrics fontMetrics = fontPaint.getFontMetrics();
            float txtHeight = fontMetrics.descent - fontMetrics.ascent;
            float txtWidth = fontPaint.measureText(number) > txtHeight ? fontPaint.measureText(number) : txtHeight;
            int hron = (int) Math.ceil(txtWidth + txtPadding * 2);
            rect.left = mWidth - hron - distance;
            rect.right = mWidth - distance;
            rect.top = 0;
            rect.bottom = (int) Math.ceil(txtHeight + txtPadding * 2);
            int centerX = (rect.left + rect.right) / 2;
            int centerY = (rect.top + rect.bottom) / 2;
            centerPoint.set(centerX, centerY);

            int fontX = rect.centerX();
            int fontY = (int) ((rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2);
            fontPoint.set(fontX, fontY);
        } else {
            int hron = (int) Math.ceil(txtPadding * 2);
            rect.left = mWidth - hron - distance;
            rect.right = mWidth - distance;
            rect.top = 0;
            rect.bottom = (int) Math.ceil(txtPadding * 2);
        }
        invalidate();
    }
}
