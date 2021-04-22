package com.rails.lib_data.bean;


import java.io.Serializable;

/**
 * 张少婷
 * 地址管理页面对象
 */
public class AddressBean implements Serializable {


    /**
     * addressId : 0
     * distributeFlag : 0
     * email : string
     * emergencyPerson : string
     * emergencyPhone : string
     * fullAddress : string
     * hasDefault : 0
     * invoiceAddress : 0
     * mobile : string
     * operateFlag : 0
     * phone : string
     * receiverName : string
     * receivingAddress : 0
     * returnAddress : 0
     * zip : string
     */

    private long addressId;
    private int distributeFlag;
    private String email;
    private String emergencyPerson;
    private String emergencyPhone;
    private String fullAddress;
    private int hasDefault;
    private int invoiceAddress;
    private String mobile;
    private int operateFlag;
    private String phone;
    private String receiverName;
    private int receivingAddress;
    private int returnAddress;
    private String zip;
    private Boolean isdefault;
    private String code;
    private String msg;


    /**
     * 专属收货地址
     */
    private String id;
    private String key;
    private String platformId;
    private String buyerId;
    private String organizeId;
    private String provinceCode;
    private String cityCode;
    private String countryCode;
    private String townCode;
    private String villageCode;
    private String attachAddress;
    private String hasInvoiceDefault;
    private String created;
    private String modified;
    private String yn;
    private String icon;

//    public final ObservableField<Boolean> isSel = new ObservableField<>();

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public int getDistributeFlag() {
        return distributeFlag;
    }

    public void setDistributeFlag(int distributeFlag) {
        this.distributeFlag = distributeFlag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyPerson() {
        return emergencyPerson;
    }

    public void setEmergencyPerson(String emergencyPerson) {
        this.emergencyPerson = emergencyPerson;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getHasDefault() {
        return hasDefault;
    }

    public void setHasDefault(int hasDefault) {
        this.hasDefault = hasDefault;
    }

    public int getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(int invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(int operateFlag) {
        this.operateFlag = operateFlag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(int receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public int getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(int returnAddress) {
        this.returnAddress = returnAddress;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

//    public ObservableField<Boolean> getIsSel() {
//        return isSel;
//    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getAttachAddress() {
        return attachAddress;
    }

    public void setAttachAddress(String attachAddress) {
        this.attachAddress = attachAddress;
    }

    public String getHasInvoiceDefault() {
        return hasInvoiceDefault;
    }

    public void setHasInvoiceDefault(String hasInvoiceDefault) {
        this.hasInvoiceDefault = hasInvoiceDefault;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
