package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.rails.purchaseplatform.common.R;

public class FiveStarView extends LinearLayout {
    private Context mContext;
    private LinearLayout mFiveStar;

    public FiveStarView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public FiveStarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public FiveStarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FiveStarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_five_star, null);
        mFiveStar = view.findViewById(R.id.ll_star);
        this.addView(view);
    }

    public void setStar(double number) {
        if (number <= 0) return;
        for (int i = 0; i < number && i < 5; i++) {
            if (number - i < 1 && number - i != 0) {
                ((ImageView) mFiveStar.getChildAt(i)).setImageResource(R.mipmap.ic_half_star);
            } else {
                ((ImageView) mFiveStar.getChildAt(i)).setImageResource(R.mipmap.ic_star);
            }
        }
    }
}
