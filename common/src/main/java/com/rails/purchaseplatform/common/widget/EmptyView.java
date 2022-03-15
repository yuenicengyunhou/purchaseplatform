package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.rails.purchaseplatform.common.databinding.ViewEmptyBinding;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import androidx.annotation.Nullable;

/**
 * 列表空view
 * author wangchao
 * date   on 2018/7/10.
 */

public class EmptyView extends LinearLayout {

    ViewEmptyBinding binding;

    public EmptyView(Context context) {
        super(context);
        init(context, null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ViewEmptyBinding.inflate(inflater, this, true);
    }


    /**
     * 设置图标
     *
     * @param res
     * @return
     */
    public EmptyView setImgEmpty(int res) {
        if (res == 0) {
            binding.imgEmpty.setVisibility(View.GONE);
        } else {
            binding.imgEmpty.setVisibility(View.VISIBLE);
            binding.imgEmpty.setImageResource(res);
        }

        return this;
    }


    /**
     * 设置提示内容
     *
     * @param content
     * @return
     */
    public EmptyView setContentEmpty(String content) {
        binding.tvContentEmpty.setVisibility(VISIBLE);
        binding.tvContentEmpty.setText(content);
        return this;
    }

    public EmptyView setContentEmpty(int str) {
        binding.tvContentEmpty.setText(str);
        return this;
    }

    /**
     * @param des
     * @return
     */
    public EmptyView setDescEmpty(String des) {
        if (TextUtils.isEmpty(des))
            binding.tvDesc.setVisibility(View.GONE);
        else {
            binding.tvDesc.setVisibility(View.VISIBLE);
            binding.tvDesc.setText(des);
        }
        return this;
    }

    public EmptyView setDescEmpty(int des) {
        if (des == -1)
            binding.tvDesc.setVisibility(View.GONE);
        else {
            binding.tvDesc.setVisibility(View.VISIBLE);
            binding.tvDesc.setText(des);
        }
        return this;
    }


    /**
     * @param str
     * @return
     */
    public EmptyView setBtnUnempty(String str) {
        if (TextUtils.isEmpty(str))
            binding.btnUnEmpty.setVisibility(View.GONE);
        else {
            binding.btnUnEmpty.setVisibility(View.VISIBLE);
            binding.btnUnEmpty.setText(str);
        }
        return this;
    }


    /**
     * @param str
     * @return
     */
    public EmptyView setBtnEmpty(String str) {
        if (TextUtils.isEmpty(str))
            binding.btnEmpty.setVisibility(View.GONE);
        else {
            binding.btnEmpty.setVisibility(View.VISIBLE);
            binding.btnEmpty.setText(str);
        }
        return this;
    }

    public boolean isBtnEmptyVisible() {
        return binding.btnEmpty.getVisibility() == View.VISIBLE;
    }

    public EmptyView setBtnGobuy(String str) {
        if (TextUtils.isEmpty(str))
            binding.btnGobuy.setVisibility(View.GONE);
        else {
            binding.btnGobuy.setVisibility(VISIBLE);
            binding.btnGobuy.setText(str);
        }
        return this;
    }


    /**
     * @param str
     * @return
     */
    public EmptyView setBtnEmpty(int str) {
        if (str == -1)
            binding.btnEmpty.setVisibility(View.GONE);
        else {
            binding.btnEmpty.setVisibility(View.VISIBLE);
            binding.btnEmpty.setText(str);
        }
        return this;
    }


    /**
     * 设置点击事件
     *
     * @param onClickListener
     * @return
     */
    public EmptyView setListener(OnClickListener onClickListener) {
        if (binding.btnUnEmpty.getVisibility() == View.VISIBLE)
            binding.btnUnEmpty.setOnClickListener(onClickListener);

        if (binding.btnEmpty.getVisibility() == View.VISIBLE)
            binding.btnEmpty.setOnClickListener(onClickListener);

        binding.flEmpty.setOnClickListener(onClickListener);

        return this;
    }


    public EmptyView setBtnListener(OnClickListener onClickListener) {
        binding.btnUnEmpty.setOnClickListener(onClickListener);
        binding.btnEmpty.setOnClickListener(onClickListener);
        return this;
    }

    public EmptyView setGoBuyListener(OnClickListener onClickListener) {
        binding.btnGobuy.setOnClickListener(onClickListener);
        return this;
    }


    public EmptyView setMarginTop(int marginTop) {
        if (marginTop != 0) {
            binding.llEmpty.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.llEmpty.getLayoutParams();
            params.topMargin = ScreenSizeUtil.dp2px(getContext(), marginTop);
        } else {
            binding.llEmpty.setGravity(Gravity.CENTER);
        }
        return this;
    }

    /**
     * 设置背景颜色
     */
    public void setBackgroundColor(int color) {
        binding.flEmpty.setBackgroundColor(color);

    }

}

