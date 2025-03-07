package com.rails.lib_data.bean;

import java.io.Serializable;
import java.util.List;

public class OrderFilterBean implements Serializable {

//    private String lowPrice;

//    private String highPrice;
    private String startDate;

    private String endDate;

    private List<OrderStatusBean> statusBeans;

    private String goodsType = "0";//0通用  1专用

//    private String checkedCode;
//
//    public String getCheckedCode() {
//        return checkedCode;
//    }
//
//    public void setCheckedCode(String checkedCode) {
//        this.checkedCode = checkedCode;
//    }

//    public String getLowPrice() {
//        return lowPrice;
//    }

//    public void setLowPrice(String lowPrice) {
//        this.lowPrice = lowPrice;
//    }
//
//    public String getHighPrice() {
//        return highPrice;
//    }
//
//    public void setHighPrice(String highPrice) {
//        this.highPrice = highPrice;
//    }


    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<OrderStatusBean> getStatusBeans() {
        return statusBeans;
    }

    public void setStatusBeans(List<OrderStatusBean> statusBeans) {
        this.statusBeans = statusBeans;
    }

    public void clearEndDate() {
        this.endDate = "";
    }

    public void clearStartDate() {
        this.startDate = "";
    }

//    public void clearLowPrice() {
//        this.lowPrice = "";
//    }
//
//    public void clearHighPrice() {
//        this.highPrice = "";
//    }

    public void resetStatusBeans() {
        for (int i = 0; i < statusBeans.size(); i++) {
            OrderStatusBean bean = statusBeans.get(i);
            if (i == 0) {
                bean.setChecked(true);
            } else {
                bean.setChecked(false);
            }
        }
    }

    public void resetFilter() {
        clearEndDate();
        clearStartDate();
//        clearHighPrice();
//        clearLowPrice();
        resetStatusBeans();
    }
}
