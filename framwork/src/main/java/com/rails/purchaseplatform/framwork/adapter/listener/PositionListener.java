package com.rails.purchaseplatform.framwork.adapter.listener;

/**
 * list&grid item onclick
 */
public interface PositionListener<T> {

    /**
     * @param bean
     * @param position
     */
    void onPosition(T bean, int position);
}