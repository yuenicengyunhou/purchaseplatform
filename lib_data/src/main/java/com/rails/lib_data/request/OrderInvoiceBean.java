package com.rails.lib_data.request;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderInvoiceBean {
    private InvoiceAddressBean invoiceAddress;
    private Integer invoiceTitleId;
    private Integer invoiceType;
    private Integer content;
    private Integer invoiceModality;

    public InvoiceAddressBean getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(InvoiceAddressBean invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Integer getInvoiceTitleId() {
        return invoiceTitleId;
    }

    public void setInvoiceTitleId(Integer invoiceTitleId) {
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
