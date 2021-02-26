package com.rails.lib_data.bean;

import java.util.ArrayList;

/**
 * 对分页列表进行处理
 * author:wangchao
 * date:2018/11/5
 */
public class ListBeen<T> {


    private int pageTotal;
    private ArrayList<T> list;
    private int Total;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
