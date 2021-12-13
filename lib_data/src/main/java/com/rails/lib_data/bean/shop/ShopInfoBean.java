package com.rails.lib_data.bean.shop;

import android.text.TextUtils;

public class ShopInfoBean {


    /**
     * id : 1409
     * key : 1409
     * platformId : 20
     * sellerId : null
     * shopId : 202003030111
     * organizeId : 58959
     * organizeName : 得力集团有限公司
     * shopName : 1111
     * shortCode : null
     * shopType : 2
     * shopTypeView : 线下店铺
     * shopStatus : 90
     * shopUrl : null
     * logoUrl : //xsky.rails.cn/mall/a98845b26b3a1cb9946326696c64bcd320210420114942014.jpg
     * keyword : null
     * introduce : null
     * mainSell : null
     * provinceCode : null
     * cityCode : null
     * districtCode : null
     * detailAddress : null
     * fullAddress : null
     * zcode : null
     * mobile : 15558297346
     * landline : null
     * email : null
     * dataRejectReason : null
     * qrcodeUrl : null
     * remark : null
     * operatorId : null
     * yn : 1
     * supplierStatus : 2
     * creditLevel : B
     * tiezongCode : 25438442-X01
     * recommendOrgId : null
     * bindOrgId : null
     */

    private String id;
    private int key;
    private String platformId;
    private Object sellerId;
    private long shopId;
    private int organizeId;
    private String organizeName = "";
    private String shopName;
    private Object shortCode;
    private int shopType;
    private String shopTypeView;
    private int shopStatus;
    private Object shopUrl;
    private String logoUrl;
    private Object keyword;
    private Object introduce;
    private Object mainSell;
    private Object provinceCode;
    private Object cityCode;
    private Object districtCode;
    private Object detailAddress;
    private Object fullAddress;
    private Object zcode;
    private String mobile="";
    private Object landline;
    private Object email;
    private Object dataRejectReason;
    private Object qrcodeUrl;
    private Object remark;
    private Object operatorId;
    private int yn;
    private int supplierStatus;
    private String creditLevel;
    private String tiezongCode;
    private Object recommendOrgId;
    private Object bindOrgId;
    private int materialType;

    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public Object getSellerId() {
        return sellerId;
    }

    public void setSellerId(Object sellerId) {
        this.sellerId = sellerId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(int organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeName() {
        if (null == organizeName) {
            return "";
        }
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Object getShortCode() {
        return shortCode;
    }

    public void setShortCode(Object shortCode) {
        this.shortCode = shortCode;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public String getShopTypeView() {
        return shopTypeView;
    }

    public void setShopTypeView(String shopTypeView) {
        this.shopTypeView = shopTypeView;
    }

    public int getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(int shopStatus) {
        this.shopStatus = shopStatus;
    }

    public Object getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(Object shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Object getKeyword() {
        return keyword;
    }

    public void setKeyword(Object keyword) {
        this.keyword = keyword;
    }

    public Object getIntroduce() {
        return introduce;
    }

    public void setIntroduce(Object introduce) {
        this.introduce = introduce;
    }

    public Object getMainSell() {
        return mainSell;
    }

    public void setMainSell(Object mainSell) {
        this.mainSell = mainSell;
    }

    public Object getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Object provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Object getCityCode() {
        return cityCode;
    }

    public void setCityCode(Object cityCode) {
        this.cityCode = cityCode;
    }

    public Object getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Object districtCode) {
        this.districtCode = districtCode;
    }

    public Object getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(Object detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Object getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(Object fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Object getZcode() {
        return zcode;
    }

    public void setZcode(Object zcode) {
        this.zcode = zcode;
    }

    public String getMobile() {
        if (null == mobile) {
            return "";
        }
        return mobile;
    }

    public void setMobile(String mobile) {
        if (null == mobile) {
            mobile = "";
        }
        this.mobile = mobile;
    }

    public Object getLandline() {
        return landline;
    }

    public void setLandline(Object landline) {
        this.landline = landline;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getDataRejectReason() {
        return dataRejectReason;
    }

    public void setDataRejectReason(Object dataRejectReason) {
        this.dataRejectReason = dataRejectReason;
    }

    public Object getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(Object qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Object operatorId) {
        this.operatorId = operatorId;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public int getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(int supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getCreditLevel() {
        if (null == creditLevel) {
            creditLevel = "";
        }
        switch (creditLevel) {
            case "A":
            case "B":
                return "风险较低";
            case "D":
            case "C":
                return "风险较高";
        }
        return "";
    }

    public boolean showMarkImg() {
        if (null == creditLevel || TextUtils.isEmpty(creditLevel)) {
            return false;
        } else {
            return true;
        }
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getTiezongCode() {
        return tiezongCode;
    }

    public void setTiezongCode(String tiezongCode) {
        this.tiezongCode = tiezongCode;
    }

    public Object getRecommendOrgId() {
        return recommendOrgId;
    }

    public void setRecommendOrgId(Object recommendOrgId) {
        this.recommendOrgId = recommendOrgId;
    }

    public Object getBindOrgId() {
        return bindOrgId;
    }

    public void setBindOrgId(Object bindOrgId) {
        this.bindOrgId = bindOrgId;
    }
}
