package com.rails.purchaseplatform.order.bean;

public  class OrderBean {
    private String danHao;
    private String time;
    private String address;
    private String title;
    private String money;
    private String count;
    private String type;
    private String total;
    private int img;

    public OrderBean(String danHao, String time, String address, String title, String money, String count, String type, String total, int img) {
        this.danHao = danHao;
        this.time = time;
        this.address = address;
        this.title = title;
        this.money = money;
        this.count = count;
        this.type = type;
        this.total = total;
        this.img = img;
    }

    public String getDanHao() {
        return danHao;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public String getMoney() {
        return money;
    }

    public String getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public String getTotal() {
        return total;
    }

    public int getImg() {
        return img;
    }

    public void setDanHao(String danHao) {
        this.danHao = danHao;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
