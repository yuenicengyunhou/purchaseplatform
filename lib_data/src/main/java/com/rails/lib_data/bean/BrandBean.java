package com.rails.lib_data.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 品牌bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class BrandBean implements Serializable{


    /**
     * id : 777
     * key : 777
     * platformId : 20
     * pictureUrl : //oss.mall.95306.cn/mall/886f11f8f319aa9230681bf2e2faf65120210203170133184.jpg
     * linkUrl : https://mall.95306.cn/mall-view/shop?shopId=202005140010
     * redirectionStatus : 1
     * sortNumber : 1
     * operatorId : 1000091795
     * operatorName : gtscyy05
     * created : 2021-03-01 08:41:36
     * modified : 2021-03-01 08:41:36
     * yn : 1
     */



    private String id;
    private String key;
    private String platformId;
    private String pictureUrl;
    private String linkUrl;
    private String redirectionStatus;
    private String sortNumber;
    private String operatorId;
    private String operatorName;
    private String created;
    private String modified;
    private String yn;

    private String brandId;
    private String brandName;
    private String brandNameE;
    private String brandLogoUrl;
    private String createTime;
    private String countNum;
    private String shopId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getRedirectionStatus() {
        return redirectionStatus;
    }

    public void setRedirectionStatus(String redirectionStatus) {
        this.redirectionStatus = redirectionStatus;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
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

    public String getYn() {
        return yn;
    }

    public void setYn(String yn) {
        this.yn = yn;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getShopid() {
        return shopId;
    }

    public void setShopid(String shopid) {
        this.shopId = shopid;
    }

    public String getBrandName() {
        if (TextUtils.isEmpty(brandName))
            return "";
        else {
            if (brandName.contains("(")) {
                return brandName.substring(0, brandName.indexOf("("));
            } else
                return brandName;
        }
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandNameE() {
        if (TextUtils.isEmpty(brandName))
            return "";
        else {
            if (brandName.contains("(")) {
                return brandName.substring(brandName.indexOf("(") + 1, brandName.indexOf(")"));
            } else
                return brandName;
        }
    }

    public void setBrandNameE(String brandNameE) {
        this.brandNameE = brandNameE;
    }

    public String getBrandLogoUrl() {
        return brandLogoUrl;
    }

    public void setBrandLogoUrl(String brandLogoUrl) {
        this.brandLogoUrl = brandLogoUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }
}
