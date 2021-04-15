package com.rails.lib_data.bean;

/**
 * 首页专区
 * @author： sk_comic@163.com
 * @date: 2021/4/14
 */
public class NavigationBean {


    private Integer id;
    private Integer key;
    private Integer platformId;
    private Integer sortNumber;
    private String navigationBarName;
    private String linkUrl;
    private Integer redirectionStatus;
    private Integer operatorId;
    private String operatorName;
    private String created;
    private String modified;
    private Integer yn;
    private Integer isNew;
    private int res;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getNavigationBarName() {
        return navigationBarName;
    }

    public void setNavigationBarName(String navigationBarName) {
        this.navigationBarName = navigationBarName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getRedirectionStatus() {
        return redirectionStatus;
    }

    public void setRedirectionStatus(Integer redirectionStatus) {
        this.redirectionStatus = redirectionStatus;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
