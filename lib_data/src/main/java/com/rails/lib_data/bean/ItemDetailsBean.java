package com.rails.lib_data.bean;

public class ItemDetailsBean {

    private int id;
    private String type;
    private String name;
    private int price;
    private String describe;
    private int score;
    private int saleCounts;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSaleCounts() {
        return saleCounts;
    }

    public void setSaleCounts(int saleCounts) {
        this.saleCounts = saleCounts;
    }
}
