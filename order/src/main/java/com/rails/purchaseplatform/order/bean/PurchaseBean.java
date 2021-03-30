package com.rails.purchaseplatform.order.bean;

import java.util.List;

@Deprecated
public class PurchaseBean {
    public PurchaseBean(String danHao, String time, String address, List<Student> list) {
        this.danHao = danHao;
        this.time = time;
        this.address = address;
        this.list = list;
    }

    public String danHao;
    public String time;
    public String address;
    public List<Student> list;
    public static class Student{
        public Student(String title, String money, String count, String type, String total, int img) {
            this.title = title;
            this.money = money;
            this.count = count;
            this.type = type;
            this.total = total;
            this.img = img;
        }

        public String title;
        public String money;
        public String count;
        public String type;
        public String total;
        public int img;
    }

}
