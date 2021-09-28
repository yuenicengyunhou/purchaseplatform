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

public class SpaceRightDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int bgColor;


    /**
     * @param context
     * @param space   recyclerview 间隔 divier
     * @param color   recyclerview的背景颜色
     */
    public SpaceRightDecoration(Context context, int space, int color) {
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
        int size = parent.getLayoutManager().getItemCount();
        int position = parent.getChildAdapterPosition(view);

        if (position == size - 1)
            outRect.right = 0;
        else
            outRect.right = space;

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // TODO: 2017/12/20  对item的绘制，暂时无用
    }

}
