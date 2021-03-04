package com.rails.purchaseplatform.common.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/3
 */
public class PageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_ALPHA = 0.0f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();    //得到view宽

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left. 出了左边屏幕
            page.setAlpha(0);

        } else if (position <= 1) {
            if (position < 0) {
                page.setTranslationX(-pageWidth * position);  //阻止消失页面的滑动
            } else {
                page.setTranslationX(pageWidth);        //直接设置出现的页面到底
                page.setTranslationX(-pageWidth * position);  //阻止出现页面的滑动
            }
            float alphaFactor = Math.max(MIN_ALPHA, 1 - Math.abs(position));
            page.setAlpha(alphaFactor);
        } else {
            page.setAlpha(0);
        }
    }
}
