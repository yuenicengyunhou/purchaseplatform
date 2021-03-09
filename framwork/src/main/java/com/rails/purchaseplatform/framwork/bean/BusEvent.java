package com.rails.purchaseplatform.framwork.bean;

/**
 * 发送消息时间
 * author :Created by sk.
 * date : 2016/9/30.
 * email: wangchao@lepu.cn
 */

public class BusEvent<T> {

    private T bean;
    private String eventCode;

    public BusEvent(T bean) {
        this.bean = bean;
    }

    public BusEvent(String eventCode) {
        this.eventCode = eventCode;
    }

    public BusEvent(T bean, String eventCode) {
        this.bean = bean;
        this.eventCode = eventCode;
    }

    public T getBean() {
        return bean;
    }

    public String getEventCode() {
        return eventCode;
    }
}
