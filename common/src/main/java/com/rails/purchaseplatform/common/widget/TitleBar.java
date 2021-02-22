package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.rails.purchaseplatform.common.databinding.ViewTitleBarBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;




/**
 * 定义头部view
 * author wangchao
 * date   on 2018/1/8.
 */

public class TitleBar extends Toolbar {

    ViewTitleBarBinding binding;


    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ViewTitleBarBinding.inflate(inflater, this, true);
    }


    /**
     * 设置标题
     *
     * @param tvTitleContent
     * @return
     */
    public TitleBar setTitle(String tvTitleContent) {
        binding.commentTitle.setVisibility(TextUtils.isEmpty(tvTitleContent) ? View.GONE : View.VISIBLE);
        binding.commentTitle.setText(tvTitleContent);
        return this;
    }

    /**
     * 设置标题
     *
     * @param tvTitleContent
     * @return
     */
    public TitleBar setTitleBar(int tvTitleContent) {
        binding.commentTitle.setVisibility(tvTitleContent > 0 ? View.VISIBLE : View.GONE);
        binding.commentTitle.setText(tvTitleContent);
        return this;
    }

    /**
     * 标题右侧设置角标
     *
     * @param res
     * @return
     */
    public TitleBar setTitleRightRes(int res) {
        Drawable valueRightIcon = getResources().getDrawable(res);
        binding.commentTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, valueRightIcon, null);
        return this;
    }


    /**
     * 设置标题颜色
     *
     * @param tvTitleColor
     * @return
     */
    public TitleBar setTitleColor(int tvTitleColor) {
        binding.commentTitle.setTextColor(getResources().getColor(tvTitleColor));
        return this;
    }


    /**
     * 设置标题尺寸
     *
     * @param tvTitleSize
     * @return
     */
    public TitleBar setTitleSize(int tvTitleSize) {
        binding.commentTitle.setTextSize(tvTitleSize);
        return this;
    }


    /**
     * 设置标题的图片
     *
     * @param imgTitleRes
     * @return
     */
    public TitleBar setImageTitle(int imgTitleRes) {
        binding.imgTitle.setImageResource(imgTitleRes);
        return this;
    }


    /**
     * 设置右侧按钮内容
     *
     * @param btnRightContent
     * @return
     */
    public TitleBar setBtnRightContent(String btnRightContent) {
        binding.btnRight.setVisibility(TextUtils.isEmpty(btnRightContent) ? View.GONE : View.VISIBLE);
        binding.btnRight.setText(btnRightContent);
        return this;
    }

    /**
     * 设置右侧按钮内容
     *
     * @param btnRightContent
     * @return
     */
    public TitleBar setBtnRightContent(int btnRightContent) {
        binding.btnRight.setVisibility(btnRightContent > 0 ? View.VISIBLE : View.GONE);
        binding.btnRight.setText(btnRightContent);
        return this;
    }

    /**
     * @param isShow
     * @return
     */
    public TitleBar setShowLine(boolean isShow) {
        if (isShow)
            binding.line.setVisibility(View.VISIBLE);
        else
            binding.line.setVisibility(View.GONE);
        return this;
    }


    /**
     * 设置右侧按钮颜色
     *
     * @param btnRightColor
     * @return
     */
    public TitleBar setBtnRightColor(int btnRightColor) {
        binding.btnRight.setTextColor(getResources().getColor(btnRightColor));
        return this;
    }


    /**
     * 设置右侧按钮右侧
     *
     * @param resId
     * @return
     */
    public TitleBar setBtnRightBackground(int resId) {
        binding.btnRight.setBackgroundResource(resId);
        return this;
    }


    /**
     * 设置右侧按钮文字大小
     *
     * @param btnRightSize
     * @return
     */
    public TitleBar setBtnRightSize(int btnRightSize) {
        binding.btnRight.setTextSize(btnRightSize);
        return this;
    }


    /**
     * 设置右侧图片
     *
     * @param imgRightRes
     * @return
     */
    public TitleBar setImgRightRes(int imgRightRes) {
        binding.imgRight.setImageResource(imgRightRes);
        return this;
    }


    /**
     * 设置右侧按钮点击事件
     *
     * @param onClickListener
     * @return
     */
    public TitleBar setRightListener(OnClickListener onClickListener) {
        if (binding.btnRight.getVisibility() == View.VISIBLE)
            binding.btnRight.setOnClickListener(onClickListener);

        if (binding.imgRight.getVisibility() == View.VISIBLE)
            binding.imgRight.setOnClickListener(onClickListener);
        return this;
    }


    /**
     * 设置中部点击事件
     *
     * @param onClickListener
     * @return
     */
    public TitleBar setCenterListener(OnClickListener onClickListener) {
        if (binding.commentTitle.getVisibility() == View.VISIBLE)
            binding.commentTitle.setOnClickListener(onClickListener);

        if (binding.imgTitle.getVisibility() == View.VISIBLE)
            binding.imgTitle.setOnClickListener(onClickListener);
        return this;
    }


    /**
     * 设置左侧按钮文字内容
     *
     * @param btnRightContent
     * @return
     */
    public TitleBar setBtnLeftContent(String btnRightContent) {
        binding.btnLeft.setVisibility(TextUtils.isEmpty(btnRightContent) ? View.GONE : View.VISIBLE);
        binding.btnLeft.setText(btnRightContent);
        return this;
    }

    /**
     * 设置左侧按钮文字内容
     *
     * @param btnLeftContent
     * @return
     */
    public TitleBar setBtnLeftContent(int btnLeftContent) {
        binding.btnLeft.setVisibility(btnLeftContent > 0 ? View.VISIBLE : View.GONE);
        binding.btnLeft.setText(btnLeftContent);
        return this;
    }


    /**
     * 设置左侧按钮文字颜色
     *
     * @param btnRightColor
     * @return
     */
    public TitleBar setBtnLeftColor(int btnRightColor) {
        binding.btnLeft.setTextColor(getResources().getColor(btnRightColor));
        return this;
    }


    /**
     * 设置左侧按钮文字大小
     *
     * @param btnRightSize
     * @return
     */
    public TitleBar setBtnLeftSize(int btnRightSize) {
        binding.btnLeft.setTextSize(btnRightSize);
        return this;
    }


    /**
     * 设置左侧图片
     *
     * @param imgRightRes
     * @return
     */
    public TitleBar setImgLeftRes(int imgRightRes) {
        binding.imgLeft.setImageResource(imgRightRes);
        return this;
    }


    /**
     * @param onClickListener
     * @return
     */
    public TitleBar setLeftListener(OnClickListener onClickListener) {
        if (binding.btnLeft.getVisibility() == View.VISIBLE)
            binding.btnLeft.setOnClickListener(onClickListener);

        if (binding.imgLeft.getVisibility() == View.VISIBLE)
            binding.imgLeft.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 隐藏右侧图标
     *
     * @return
     */
    public TitleBar removeRightImage() {
        binding.imgRight.setVisibility(View.GONE);
        return this;
    }

    @Deprecated
    public View build() {
        return this;
    }
}

