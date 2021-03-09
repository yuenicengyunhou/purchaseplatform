package com.rails.purchaseplatform.notice.adapter.bean;

/**
 * 公告一些模块的展示对象
 * @author： sk_comic@163.com
 * @date: 2021/3/5
 */
public class ResBean {

    private String name;
    private int res;
    private String path;

    public ResBean(String name, int res, String path) {
        this.name = name;
        this.res = res;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
