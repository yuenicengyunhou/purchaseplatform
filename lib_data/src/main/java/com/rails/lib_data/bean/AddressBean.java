package com.rails.lib_data.bean;

/**
 * 张少婷
 * 地址管理页面对象
 */
public class AddressBean {


    /**
     * name : 张三三
     * phone : 139******583
     * address : 北京市海淀区大钟寺颐和家园8号院
     * isdefault : true
     */

    private String name;
    private String phone;
    private String address;
    private Boolean isdefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }
}
