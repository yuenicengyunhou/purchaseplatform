package com.rails.lib_data.bean;

import androidx.databinding.ObservableField;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/10
 */
public class CartBean {

    private String shopName;
    public final ObservableField<Boolean> isSel = new ObservableField<>();

    public CartBean(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
