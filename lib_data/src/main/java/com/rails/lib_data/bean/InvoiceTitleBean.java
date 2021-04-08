package com.rails.lib_data.bean;

import androidx.databinding.ObservableField;

/**
 * 发票内容信息bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceTitleBean {

    private String module;
    private String company;
    private String invoiceCode;
    private String address;
    private String phone;
    private String bank;
    private String bankCode;

    public final ObservableField<Boolean> isSel = new ObservableField<>();


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

}
