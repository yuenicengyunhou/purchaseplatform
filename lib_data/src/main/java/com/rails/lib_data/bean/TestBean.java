package com.rails.lib_data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class TestBean implements Parcelable {
    protected TestBean(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
