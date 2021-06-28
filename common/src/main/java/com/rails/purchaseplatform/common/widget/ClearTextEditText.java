package com.rails.purchaseplatform.common.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.rails.purchaseplatform.common.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ClearTextEditText extends AppCompatEditText implements View.OnTouchListener, TextWatcher, View.OnFocusChangeListener {

    private Drawable mClearTextDrawable;
    private OnFocusChangeListener mOnFocusChangeListener;
    private OnTouchListener mTouchListener;

    public ClearTextEditText(@NonNull @NotNull Context context) {
        this(context, null);
        initEdit(context);
    }

    public ClearTextEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearTextEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * attr
     */
    private void initAttr() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEdit(Context context) {
        initAttr();
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.abc_ic_clear_material);
        if (null == drawable) return;
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        mClearTextDrawable = wrappedDrawable;
        mClearTextDrawable.setBounds(0, 0, mClearTextDrawable.getIntrinsicHeight(), mClearTextDrawable.getIntrinsicWidth());
        setmClearTextDrawableVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private void setmClearTextDrawableVisible(boolean visible) {
        mClearTextDrawable.setVisible(visible, false);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], visible ? mClearTextDrawable : null, compoundDrawables[3]);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        this.mOnFocusChangeListener = l;
    }


    public void setmTouchListener(OnTouchListener mTouchListener) {
        this.mTouchListener = mTouchListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        if (mClearTextDrawable.isVisible() && x > getWidth() - getPaddingEnd() - mClearTextDrawable.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setText("");
            }
            return true;
        }
        return mTouchListener != null && mTouchListener.onTouch(v, event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setmClearTextDrawableVisible(Objects.requireNonNull(getText()).length() > 0);
        } else {
            setmClearTextDrawableVisible(false);
        }
        if (null != mOnFocusChangeListener) {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()) {
            setmClearTextDrawableVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
