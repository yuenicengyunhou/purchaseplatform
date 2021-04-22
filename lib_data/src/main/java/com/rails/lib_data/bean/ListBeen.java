package com.rails.lib_data.bean;

import java.util.ArrayList;

public class ListBeen<T>{


    /**
     * firstPage : true
     * lastPage : true
     * nextPage : 0
     * pageNum : 0
     * pageSize : 0
     * prevPage : 0
     * totalCount : 0
     * totalPageCount : 0
     */

    private boolean firstPage;
    private boolean lastPage;
    private int nextPage;
    private int pageNum;
    private int pageSize;
    private int prevPage;
    private int totalCount;
    private int totalPageCount;
    private ArrayList<T> result;

    public ArrayList<T> getList() {
        return result;
    }

    public void setList(ArrayList<T> list) {
        this.result = list;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
}
