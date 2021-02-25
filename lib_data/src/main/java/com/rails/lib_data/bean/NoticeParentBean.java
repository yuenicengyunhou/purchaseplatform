package com.rails.lib_data.bean;

import java.util.ArrayList;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/25
 */
public class NoticeParentBean {

    String key;
    String tip;
    ArrayList<NoticeBean> sub;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ArrayList<NoticeBean> getSub() {
        return sub;
    }

    public void setSub(ArrayList<NoticeBean> sub) {
        this.sub = sub;
    }
}
