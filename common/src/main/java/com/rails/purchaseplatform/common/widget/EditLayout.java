package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ViewEditBinding;


/**
 * 新增设备
 */
public class EditLayout extends RelativeLayout {


    ViewEditBinding binding;
    private boolean isEdit = false;

    public EditLayout(Context context) {
        super(context);
        init(context, null);
    }


    public EditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    /**
     * 初始化view
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = ViewEditBinding.inflate(inflater, this, true);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreCompView);
            CharSequence key = typedArray.getText(R.styleable.MoreCompView_android_text);
            Drawable leftIcon = typedArray.getDrawable(R.styleable.MoreCompView_left_icon);
            Drawable valueRightIcon = typedArray.getDrawable(R.styleable.MoreCompView_value_right_icon);
            CharSequence content = typedArray.getText(R.styleable.MoreCompView_contentxt);
            Boolean isEdit = typedArray.getBoolean(R.styleable.MoreCompView_is_edit, true);
            CharSequence hint = typedArray.getText(R.styleable.MoreCompView_hint);
            int maxLength = typedArray.getInteger(R.styleable.MoreCompView_android_maxLength, Integer.MAX_VALUE);
            int inputType = typedArray.getInteger(R.styleable.MoreCompView_android_inputType, InputType.TYPE_CLASS_TEXT);
            boolean isHide = typedArray.getBoolean(R.styleable.MoreCompView_is_hide, false);
            if (!TextUtils.isEmpty(key))
                binding.tvKey.setText(key);

            if (!TextUtils.isEmpty(content))
                binding.etValue.setText(content);

            if (!TextUtils.isEmpty(hint))
                binding.etValue.setHint(hint);

            if (isHide)
                binding.line.setVisibility(View.GONE);
            else
                binding.line.setVisibility(View.VISIBLE);

            if (leftIcon != null)
                binding.tvKey.setCompoundDrawablesWithIntrinsicBounds(leftIcon, null, null, null);

            if (valueRightIcon != null)
                binding.etValue.setCompoundDrawablesWithIntrinsicBounds(null, null, valueRightIcon, null);

            binding.etValue.setInputType(inputType);
            binding.etValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

            if (isEdit) {
                binding.etValue.setFocusableInTouchMode(true);
                binding.etValue.setFocusable(true);
            } else {
                binding.etValue.setFocusableInTouchMode(false);
                binding.etValue.setFocusable(false);
            }


            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置可编辑
     *
     * @param isEdit
     */
    public void canEdit(boolean isEdit) {
        this.isEdit = isEdit;
        if (isEdit) {
            binding.etValue.setFocusableInTouchMode(true);
            binding.etValue.setFocusable(true);
            binding.etValue.requestFocus();
        } else {
            binding.etValue.setFocusableInTouchMode(false);
            binding.etValue.setFocusable(false);
        }
    }


    /**
     * 获取是否可编辑
     *
     * @return
     */
    public boolean isEdit() {
        return this.isEdit;
    }


    /**
     * @param value
     */
    public void setContent(String value) {
        if (!TextUtils.isEmpty(value)) {
            binding.etValue.setText(value);
        } else
            binding.etValue.setText("");
    }


    /**
     * 设置hint
     *
     * @param hint
     */
    public void setContentHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            binding.etValue.setHint(hint);
        }
    }

    /**
     * @return
     */
    public String getContent() {
        String content;
        if (binding.etValue == null)
            content = "";
        else
            content = binding.etValue.getText().toString().trim();
        return content;

    }
}
