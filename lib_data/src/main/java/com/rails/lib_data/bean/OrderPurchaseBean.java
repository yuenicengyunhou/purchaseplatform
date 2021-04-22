package com.rails.lib_data.bean;

/**
 * 结算单位bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderPurchaseBean {


    private String id;
    private String key;
    private String name;
    private String fullName;

    //1 集团公司集中结算  0 : 本单位结算
    private int accountingType;

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
}
