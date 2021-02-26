package com.rails.lib_data.bean;

import java.util.ArrayList;

/**
 * 对分页列表进行处理
 * author:wangchao
 * date:2018/11/5
 */
public class ListBeen<T> {


    private int totalPageCount;
    private ArrayList<T> result;
    private int totalCount;

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
