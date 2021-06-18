package com.rails.lib_data.bean.forNetRequest.productDetails;

import com.rails.lib_data.bean.AddressBean;

import java.util.ArrayList;

public class ProductDetailsStep1Bean {
    private ProductDetailsBean productDetailsBean;
    private ArrayList<AddressBean> addressBeanList;
    private String cartCount;

    public ProductDetailsBean getProductDetailsBean() {
        return productDetailsBean;
    }

    public void setProductDetailsBean(ProductDetailsBean productDetailsBean) {
        this.productDetailsBean = productDetailsBean;
    }

    public ArrayList<AddressBean> getAddressBeanList() {
        return addressBeanList;
    }

    public void setAddressBeanList(ArrayList<AddressBean> addressBeanList) {
        this.addressBeanList = addressBeanList;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }
}
