package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class ClearEditText extends AppCompatEditText {

    private Drawable clearDrawble;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        clearDrawble = getCompoundDrawables()[2];
        if (null == clearDrawble) {
//clearDrawble=getResources().getDrawable(R.dra)
        }
    }
}
