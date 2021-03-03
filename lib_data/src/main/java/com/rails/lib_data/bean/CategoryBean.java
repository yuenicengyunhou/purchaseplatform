package com.rails.lib_data.bean;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * 商城二级分类对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryBean extends BaseObservable {

    private String name;
    private ArrayList<CategorySubBean> subs;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CategorySubBean> getSubs() {
        return subs;
    }

    public void setSubs(ArrayList<CategorySubBean> subs) {
        this.subs = subs;
    }
}
