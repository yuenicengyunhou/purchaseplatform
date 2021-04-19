package com.rails.lib_data.bean;

public class AddressDTO {
    private String receiverName;


    private String fullAddress;

    private String mobile;

    private int receivingAddress;

    private int invoiceAddress;


    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(int receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public int getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(int invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }
}
