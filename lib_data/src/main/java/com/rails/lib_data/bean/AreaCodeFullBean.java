package com.rails.lib_data.bean;

import java.util.ArrayList;

public class AreaCodeFullBean {
    private String code;
    private String msg;
    private ArrayList<AreaCodeBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<AreaCodeBean> getData() {
        return data;
    }

    public void setData(ArrayList<AreaCodeBean> data) {
        this.data = data;
    }
}
