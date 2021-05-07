package com.rails.purchaseplatform.market.adapter.pdetail;

/**
 * 商品详情清单对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/7
 */
public class ProductBillBean {

    private String name;
    private String number;

    public ProductBillBean(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
