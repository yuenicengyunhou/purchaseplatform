package com.rails.purchaseplatform.market.adapter.pdetail;

/**
 * 推荐单位/服务对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/7
 */
public class ProductServiceBean {


    private int res;
    private String desc;
    private String name;

    public ProductServiceBean(int res, String desc, String name) {
        this.res = res;
        this.desc = desc;
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
