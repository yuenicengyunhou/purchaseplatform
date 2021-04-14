package com.rails.lib_data.bean;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

public class OrderProcessBean extends BaseObservable implements Serializable {

    private String state;
    private String time;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
