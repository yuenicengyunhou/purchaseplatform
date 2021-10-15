package com.rails.purchaseplatform.market.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author： sk_comic@163.com
 * @date: 2021/9/26
 */
public class CategoryTextView extends AppCompatTextView {


    private Paint paint;
    private Path path;

    private int mHeight;
    private int start;

    public CategoryTextView(Context context) {
        super(context);
        init();
    }

    public CategoryTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(getCurrentTextColor());
        canvas.drawPath(path, paint);

    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

        Paint.FontMetrics fm = getPaint().getFontMetrics();
//        int sizeHeight = (int) (fm.bottom - fm.top + fm.leading);
        int sizeHeight = (int) (fm.bottom - fm.top);
        mHeight = sizeHeight;
        int iconHeight = mHeight  / 3;
        start = (int) getPaint().measureText(text.toString());

        path = new Path();
        path.moveTo(start + (iconHeight >> 1), (mHeight >> 1) - (iconHeight >> 1));
        path.lineTo(start + iconHeight, mHeight >> 1);
        path.lineTo(start + (iconHeight >> 1), (mHeight >> 1) + (iconHeight >> 1));

        invalidate();
    }
}
