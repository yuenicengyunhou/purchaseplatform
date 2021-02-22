package com.rails.purchaseplatform.framwork.adapter.listener;

import android.view.View;

/**
 * 列表点击事件，传递view
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/1/5.
 */

public interface ViewPositionListener<T> {

    /**
     * @param position
     * @param type
     * @param view
     */
    void onPosition(T position, int type, View view);
}
