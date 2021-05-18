package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ViewLrLableLayoutBinding;

import androidx.annotation.DrawableRes;

/**
 * 左右组合组（不可编辑）  新增项目中使用
 * author wangchao
 * email  wangchao@cgw.com
 * date   2017/12/20
 */

public class LrLableLayout extends RelativeLayout {

    int valueColor;

    ViewLrLableLayoutBinding binding;

    public LrLableLayout(Context context) {
        super(context);
        init(context, null);
    }

    public LrLableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LrLableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            binding = ViewLrLableLayoutBinding.inflate(inflater, this, true);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LrLable);
            Resources res = context.getResources();
            CharSequence key = typedArray.getText(R.styleable.LrLable_key);
            int keyColor = typedArray.getColor(R.styleable.LrLable_key_color, res.getColor(R.color.font_black));
            int keySize = typedArray.getDimensionPixelSize(R.styleable.LrLable_key_size, 0);
            CharSequence content = typedArray.getText(R.styleable.LrLable_value_content);
            boolean isBold = typedArray.getBoolean(R.styleable.LrLable_isBold, false);
            valueColor = typedArray.getColor(R.styleable.LrLable_value_color, res.getColor(R.color.font_black));
            boolean isHide = typedArray.getBoolean(R.styleable.LrLable_is_hide, false);
            int valueSize = typedArray.getDimensionPixelSize(R.styleable.LrLable_value_size, 0);
            int paddingTop = typedArray.getDimensionPixelSize(R.styleable.LrLable_padding_top, 0);
            int paddingBottom = typedArray.getDimensionPixelSize(R.styleable.LrLable_padding_bottom, 0);
            Drawable leftIcon = typedArray.getDrawable(R.styleable.LrLable_left_icon);
            Drawable valueRightIcon = typedArray.getDrawable(R.styleable.LrLable_value_right_icon);
            Drawable leftImg = typedArray.getDrawable(R.styleable.LrLable_left_img);
            Drawable rightImg = typedArray.getDrawable(R.styleable.LrLable_right_img);

            if (!TextUtils.isEmpty(key))
                binding.tvKey.setText(key);
            if (keySize != 0) {
                binding.tvKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, keySize);
            }
            binding.tvKey.setTextColor(keyColor);

            if (isBold) {
                binding.tvKey.getPaint().setFakeBoldText(true);
            }

            binding.tvKey.setPadding(0, paddingTop, 0, paddingBottom);

            if (!TextUtils.isEmpty(content))
                binding.etValue.setText(content + " ");
            if (valueSize != 0) {
                binding.etValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueSize);
            }
            binding.etValue.setTextColor(valueColor);

            if (isHide)
                binding.line.setVisibility(View.GONE);
            else
                binding.line.setVisibility(View.VISIBLE);

            if (leftIcon != null)
                binding.tvKey.setCompoundDrawablesWithIntrinsicBounds(leftIcon, null, null, null);

            if (valueRightIcon != null)
                binding.etValue.setCompoundDrawablesWithIntrinsicBounds(null, null, valueRightIcon, null);
            typedArray.recycle();

            if (leftImg != null) {
                binding.imgLeft.setVisibility(View.VISIBLE);
                binding.imgLeft.setImageDrawable(leftImg);
            } else
                binding.imgLeft.setVisibility(View.GONE);

            if (rightImg != null) {
                binding.imgRight.setVisibility(View.VISIBLE);
                binding.imgRight.setImageDrawable(rightImg);
            } else {
                binding.imgRight.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param value
     */
    public void setContent(String value) {
        if (!TextUtils.isEmpty(value)) {
            binding.etValue.setText(value + " ");
            if (!"选择".equals(value))
                binding.etValue.setTextColor(valueColor);
            else
                binding.etValue.setTextColor(valueColor);
        } else {
            binding.etValue.setText("");
        }
    }

    /**
     * 设置右侧图标
     *
     * @param res
     */
    public void setValueRightIcon(@DrawableRes int res) {
        Drawable valueRightIcon = getResources().getDrawable(res);
        if (valueRightIcon != null)
            binding.etValue.setCompoundDrawablesWithIntrinsicBounds(null, null, valueRightIcon, null);
    }

    /**
     * 移除右侧图标
     */
    public void removeValueRightIcon() {
        binding.etValue.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
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

    public void setKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            binding.tvKey.setText(Html.fromHtml(key));
        }
    }


//    public void setKey(SpannableStringBuilder builder){
//        binding.tvKey.setText(builder);
//    }

    public String getKey() {
        String key;
        if (binding.tvKey == null)
            key = "";
        else
            key = binding.tvKey.getText().toString().trim();
        return key;
    }
}
