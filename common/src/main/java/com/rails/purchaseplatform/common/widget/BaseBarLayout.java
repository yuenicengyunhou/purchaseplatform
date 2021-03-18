package com.rails.purchaseplatform.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/18
 */
public class BaseBarLayout extends AppBarLayout {
    public BaseBarLayout(@NonNull Context context) {
        super(context);
        setBe();
    }

    public BaseBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBe();
    }

    public BaseBarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBe();
    }



    private void setBe(){
        AppBarLayout.Behavior behavior = (Behavior) getBehavior();
        behavior.setDragCallback(new Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return true;
            }
        });
    }



}
