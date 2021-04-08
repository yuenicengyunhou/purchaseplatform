package com.rails.lib_data.bean;

import androidx.databinding.ObservableField;

/**
 * 发票内容信息bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceContentBean {

    private String module;
    private String name;
    private int type;
    public final ObservableField<Boolean> isSel = new ObservableField<>();


    public InvoiceContentBean(String module, String name, int type) {
        this.module = module;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
