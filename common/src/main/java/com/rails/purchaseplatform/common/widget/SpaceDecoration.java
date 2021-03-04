package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

/**
 * Recyclerview 装饰
 * Created by sk on 2017/4/21.
 */

public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int bgColor;


    /**
     * @param context
     * @param space   recyclerview 间隔 divier
     * @param color   recyclerview的背景颜色
     */
    public SpaceDecoration(Context context, int space, int color) {
        this.space = ScreenSizeUtil.dp2px(context, space);
        if (color == 0)
            bgColor = Color.WHITE;
        else
            bgColor = context.getResources().getColor(color);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        parent.setBackgroundColor(bgColor);
        outRect.bottom = space;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // TODO: 2017/12/20  对item的绘制，暂时无用
    }

}
