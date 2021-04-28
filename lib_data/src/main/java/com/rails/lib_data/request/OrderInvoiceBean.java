package com.rails.lib_data.request;

import android.os.Parcel;

import com.rails.lib_data.bean.AddressBean;

import java.io.Serializable;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderInvoiceBean implements Serializable {
    private AddressBean invoiceAddress;
    private String invoiceTitleId;
    private int invoiceType;
    private int content;
    private int invoiceModality;


    public OrderInvoiceBean() {
    }


    public AddressBean getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressBean invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceTitleId() {
        return invoiceTitleId;
    }

    public void setInvoiceTitleId(String invoiceTitleId) {
        this.invoiceTitleId = invoiceTitleId;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getInvoiceModality() {
        return invoiceModality;
    }

    public void setInvoiceModality(int invoiceModality) {
        this.invoiceModality = invoiceModality;
    }

    protected OrderInvoiceBean(Parcel in) {
        invoiceTitleId = in.readString();
        invoiceType = in.readInt();
        content = in.readInt();
        invoiceModality = in.readInt();
    }

}
