package com.rails.lib_data.bean;

public class AddressDTO {

//    public AddressDTO(String provinceCode, String cityCode, String countryCode, String townCode, String villageCode, String attachAddress, String fullAddress, String receiverName, String mobile, String phone, String zip, String emergencyPerson, String emergencyPhone, Integer receivingAddress, Integer invoiceAddress, Integer returnAddress) {
//        this.provinceCode = provinceCode;
//        this.cityCode = cityCode;
//        this.countryCode = countryCode;
//        this.townCode = townCode;
//        this.villageCode = villageCode;
//        this.attachAddress = attachAddress;
//        this.fullAddress = fullAddress;
//        this.receiverName = receiverName;
//        this.mobile = mobile;
//        this.phone = phone;
//        this.zip = zip;
//        this.emergencyPerson = emergencyPerson;
//        this.emergencyPhone = emergencyPhone;
//        this.receivingAddress = receivingAddress;
//        this.invoiceAddress = invoiceAddress;
//        this.returnAddress = returnAddress;
//    }



    /**
     * 一级（省编号）
     */
    private String provinceCode;
    /**
     * 二级（市编号）
     */
    private String cityCode;
    /**
     * 三级（县、县级市、区编号）
     */
    private String countryCode;
    /**
     * 四级（镇、街道编号）
     */
    private String townCode;
    /**
     * 五级（村、社区）
     */
    private String villageCode;
    /**
     * 附加具体地址
     */
    private String attachAddress;
    /**
     * 完整地址
     */
    private String fullAddress;
    /**
     * 收货人
     */
    private String receiverName;
    /**
     * 联系人手机
     */
    private String mobile;
    /**
     * 联系人座机
     */
    private String phone;
    /**
     * 邮政编码
     */
    private String zip;
    /**
     * 紧急联系人
     */
    private String emergencyPerson;
    /**
     * 紧急联系人电话
     */
    private String emergencyPhone;
    /**
     * 是否为收货地址1:是，0:不是
     */
    private int receivingAddress;
    /**
     * 是否为发票地址1:是，0:不是
     */
    private int invoiceAddress;
    /**
     * 是否为退货地址1:是，0:不是
     */
    private int returnAddress;

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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public Integer getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(Integer receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public Integer getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Integer invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Integer getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(Integer returnAddress) {
        this.returnAddress = returnAddress;
    }
}
