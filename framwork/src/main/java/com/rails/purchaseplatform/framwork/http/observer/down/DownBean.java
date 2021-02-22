package com.rails.purchaseplatform.framwork.http.observer.down;

/**
 * author wangchao
 * date   on 2018/4/15.
 */

public class DownBean {

    private String savePath;
    private String url;
    private long countLength;
    private long readLength;


    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }
}
