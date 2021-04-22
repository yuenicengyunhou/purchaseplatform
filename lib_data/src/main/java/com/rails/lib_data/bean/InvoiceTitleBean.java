package com.rails.lib_data.bean;

import androidx.databinding.ObservableField;

/**
 * 发票内容信息bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceTitleBean {


    private String operator;
    private String modified;
    private String creator;
    private String created;
    private String id;
    private String platformId;
    private String accountId;
    private String accountName;
    private int invoiceType;
    private String invoiceTitle;
    private String itins;
    private String bank;
    private String account;
    private String address;
    private String telephone;

    public final ObservableField<Boolean> isSel = new ObservableField<>();

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getItins() {
        return itins;
    }

    public void setItins(String itins) {
        this.itins = itins;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
