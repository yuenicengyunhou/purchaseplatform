package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;


import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Recyclerview 装饰
 * Created by sk on 2017/4/21.
 */

public class SpaceGirdWeightDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int top;
    private int right;
    private int bottom;
    private int bgColor;


    /**
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param context
     * @param color   recyclerview的背景颜色
     */
    public SpaceGirdWeightDecoration(Context context, int left, int top, int right, int bottom, int color) {
        this.left = ScreenSizeUtil.dp2px(context, left);
        this.top = ScreenSizeUtil.dp2px(context, top);
        this.right = ScreenSizeUtil.dp2px(context, right);
        this.bottom = ScreenSizeUtil.dp2px(context, bottom);
        if (color == 0)
            bgColor = Color.WHITE;
        else
            bgColor = context.getResources().getColor(color);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        parent.setBackgroundColor(bgColor);
        int size = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);
        outRect.left = left;

        if (position == size - 1)
            outRect.right = right;
        else
            outRect.right = 0;
        outRect.top = top;
        outRect.bottom = bottom;
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // TODO: 2017/12/20  对item的绘制，暂时无用
    }


}
