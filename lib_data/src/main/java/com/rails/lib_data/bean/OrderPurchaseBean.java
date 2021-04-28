package com.rails.lib_data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.ObservableField;

/**
 * 结算单位bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderPurchaseBean implements Parcelable {

    public OrderPurchaseBean(){}


    private String id;
    private String key;
    private String name;
    private String fullName;

    public final ObservableField<Boolean> isSel = new ObservableField<>();

    //1 集团公司集中结算  0 : 本单位结算
    private int accountingType;

    protected OrderPurchaseBean(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        fullName = in.readString();
        accountingType = in.readInt();
    }

    public static final Creator<OrderPurchaseBean> CREATOR = new Creator<OrderPurchaseBean>() {
        @Override
        public OrderPurchaseBean createFromParcel(Parcel in) {
            return new OrderPurchaseBean(in);
        }

        @Override
        public OrderPurchaseBean[] newArray(int size) {
            return new OrderPurchaseBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAccountingType() {
        return accountingType;
    }

    public void setAccountingType(int accountingType) {
        this.accountingType = accountingType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeInt(accountingType);
    }
}
