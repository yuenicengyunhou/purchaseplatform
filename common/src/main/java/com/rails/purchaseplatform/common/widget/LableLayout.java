package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ViewLableLayoutBinding;


/**
 * 文本展示
 */
public class LableLayout extends RelativeLayout {

    ViewLableLayoutBinding binding;

    public LableLayout(Context context) {
        super(context);
        init(context, null);
    }


    public LableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LableLayout(Context context, AttributeSet attrs, int defStyle) {
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
            binding = ViewLableLayoutBinding.inflate(inflater, this, true);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreCompView);
            CharSequence key = typedArray.getText(R.styleable.MoreCompView_android_text);
            Drawable leftIcon = typedArray.getDrawable(R.styleable.MoreCompView_left_icon);
            Drawable valueRightIcon = typedArray.getDrawable(R.styleable.MoreCompView_value_right_icon);
            CharSequence content = typedArray.getText(R.styleable.MoreCompView_contentxt);
            CharSequence hint = typedArray.getText(R.styleable.MoreCompView_hint);
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


            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void setContent(Spannable spannable) {
        if (spannable != null) {
            binding.etValue.setText(spannable);
        } else
            binding.etValue.setText("");
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
