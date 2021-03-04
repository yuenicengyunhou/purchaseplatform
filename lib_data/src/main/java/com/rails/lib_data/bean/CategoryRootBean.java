package com.rails.lib_data.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import androidx.databinding.ObservableField;

/**
 * 一级分类对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategoryRootBean implements Serializable {


    public final ObservableField<Boolean> isSel = new ObservableField<>();


    private String name;
    private String fcid;
    private List<CategoryBean> secondPlatformCategoryList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFcid() {
        return fcid;
    }

    public void setFcid(String fcid) {
        this.fcid = fcid;
    }

    public List<CategoryBean> getSecondPlatformCategoryList() {
        return secondPlatformCategoryList;
    }

    public void setSecondPlatformCategoryList(List<CategoryBean> secondPlatformCategoryList) {
        this.secondPlatformCategoryList = secondPlatformCategoryList;
    }
}
