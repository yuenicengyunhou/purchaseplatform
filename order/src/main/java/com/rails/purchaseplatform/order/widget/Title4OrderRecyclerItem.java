package com.rails.purchaseplatform.order.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.rails.purchaseplatform.order.R;

public class Title4OrderRecyclerItem extends ConstraintLayout {
    private Context mContext;
    private View mTitle;

    public Title4OrderRecyclerItem(@NonNull Context context) {
        super(context);
        init(context);
    }

    public Title4OrderRecyclerItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Title4OrderRecyclerItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Title4OrderRecyclerItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        mTitle = LayoutInflater.from(mContext).inflate(R.layout.item_order_recycler_title, null);
        this.addView(mTitle);
    }

    /**
     * 设置文字
     *
     * @param texts 可变参数，String类型，长度只能为4个或者5个，否则方法直接return。
     */
    public void setText(String... texts) {
        if (texts.length != 4 && texts.length != 5) return;
        ((TextView) mTitle.findViewById(R.id.tv_orderNumValue)).setText(texts[0]);
        ((TextView) mTitle.findViewById(R.id.tv_generateTimeValue)).setText(texts[1]);
        ((TextView) mTitle.findViewById(R.id.tv_providerValue)).setText(texts[2]);
        ((TextView) mTitle.findViewById(R.id.tv_buyerValue)).setText(texts[3]);
        if (texts.length == 5) {
            if (texts[4].equals("")) {
                mTitle.findViewById(R.id.tv_delayTimeKey).setVisibility(View.GONE);
                mTitle.findViewById(R.id.tv_delayTimeValue).setVisibility(View.GONE);
            }
            ((TextView) mTitle.findViewById(R.id.tv_delayTimeValue)).setText(texts[4]);
        } else {
            mTitle.findViewById(R.id.tv_delayTimeKey).setVisibility(View.GONE);
            mTitle.findViewById(R.id.tv_delayTimeValue).setVisibility(View.GONE);
        }
    }
}
