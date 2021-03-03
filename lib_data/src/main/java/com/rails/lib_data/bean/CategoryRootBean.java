package com.rails.lib_data.bean;

import androidx.databinding.ObservableField;

/**
 * 一级分类对象
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategoryRootBean {


    public final ObservableField<String> navigationBarName = new ObservableField<>();
    public final ObservableField<Boolean> isSel = new ObservableField<>();
    /**
     * id : 345
     * key : 345
     * platformId : 20
     * sortNumber : 1
     * navigationBarName : 电子产品专区
     * linkUrl : https://mall.95306.cn/mall-view/product/search?businessType=1&cid=1000261
     * redirectionStatus : 1
     * operatorId : 1000090927
     * operatorName : gtwzyy01
     * created : 2021-02-19 21:39:53
     * modified : 2021-02-19 21:39:53
     * yn : 1
     * isNew : 0
     */

    private Integer id;
    private Integer key;
    private Integer platformId;
    private Integer sortNumber;
    private String linkUrl;
    private Integer redirectionStatus;
    private Integer operatorId;
    private String operatorName;
    private String created;
    private String modified;
    private Integer yn;
    private Integer isNew;

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
}
