package com.rails.purchaseplatform.framwork.adapter.listener;

/**
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/1/5.
 */

public interface MulPositionListener<T> {

    /**
     * @param position
     * @param bean
     * @param params
     */
    void onPosition(T bean, int position, int... params);

}
