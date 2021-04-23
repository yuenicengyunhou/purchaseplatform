package com.rails.lib_data.request;

import com.rails.lib_data.bean.InvoiceTitleBean;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderInvoiceBean {
    private InvoiceTitleBean invoiceAddress;
    private String invoiceTitleId;
    private Integer invoiceType;
    private Integer content;
    private Integer invoiceModality;

    public InvoiceTitleBean getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(InvoiceTitleBean invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceTitleId() {
        return invoiceTitleId;
    }

    public void setInvoiceTitleId(String invoiceTitleId) {
        this.invoiceTitleId = invoiceTitleId;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public Integer getInvoiceModality() {
        return invoiceModality;
    }

    public void setInvoiceModality(Integer invoiceModality) {
        this.invoiceModality = invoiceModality;
    }
}
