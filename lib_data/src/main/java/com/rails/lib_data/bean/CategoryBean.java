package com.rails.lib_data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * 商城二级分类对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryBean extends BaseObservable implements Parcelable {

    //"name": "紧固密封件",
//"fcid": 1001733,
    private String name;
    private String fcid;
    private ArrayList<CategorySubBean> thirdPlatformCategoryList;

    protected CategoryBean(Parcel in) {
        name = in.readString();
        fcid = in.readString();
    }

    public static final Creator<CategoryBean> CREATOR = new Creator<CategoryBean>() {
        @Override
        public CategoryBean createFromParcel(Parcel in) {
            return new CategoryBean(in);
        }

        @Override
        public CategoryBean[] newArray(int size) {
            return new CategoryBean[size];
        }
    };

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

    public ArrayList<CategorySubBean> getThirdPlatformCategoryList() {
        return thirdPlatformCategoryList;
    }

    public void setThirdPlatformCategoryList(ArrayList<CategorySubBean> thirdPlatformCategoryList) {
        this.thirdPlatformCategoryList = thirdPlatformCategoryList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fcid);
    }
}
