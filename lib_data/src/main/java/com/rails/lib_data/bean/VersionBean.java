package com.rails.lib_data.bean;

/**
 * @author： sk_comic@163.com
 * @date: 2021/5/21
 */
public class VersionBean {


    private String id;
    private String platformId;
    private String created;
    private String modified;
    private String yn;
    private String currentVersionCode;
    private String lastVersionCode;
    private String url;
    private String isForce;
    private String remork;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getYn() {
        return yn;
    }

    public void setYn(String yn) {
        this.yn = yn;
    }

    public String getCurrentVersionCode() {
        return currentVersionCode;
    }

    public void setCurrentVersionCode(String currentVersionCode) {
        this.currentVersionCode = currentVersionCode;
    }

    public String getLastVersionCode() {
        return lastVersionCode;
    }

    public void setLastVersionCode(String lastVersionCode) {
        this.lastVersionCode = lastVersionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsForce() {
        if ("1".equals(isForce))
            return true;
        else
            return false;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getRemork() {
        return remork;
    }

    public void setRemork(String remork) {
        this.remork = remork;
    }
}
