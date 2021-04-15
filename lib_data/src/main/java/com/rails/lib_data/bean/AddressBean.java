package com.rails.lib_data.bean;


import java.io.Serializable;

import androidx.databinding.ObservableField;

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

    public final ObservableField<Boolean> isSel = new ObservableField<>();

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

    public ObservableField<Boolean> getIsSel() {
        return isSel;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }
}
