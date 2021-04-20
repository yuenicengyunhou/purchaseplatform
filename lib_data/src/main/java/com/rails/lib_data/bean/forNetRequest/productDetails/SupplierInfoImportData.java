package com.rails.lib_data.bean.forNetRequest.productDetails;

public class SupplierInfoImportData {
    private int id;
    private String recommendOrg;
    private String bindOrgName;
    private String accountName;


    // ===========================================================================================


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecommendOrg() {
        return recommendOrg;
    }

    public void setRecommendOrg(String recommendOrg) {
        this.recommendOrg = recommendOrg;
    }

    public String getBindOrgName() {
        return bindOrgName;
    }

    public void setBindOrgName(String bindOrgName) {
        this.bindOrgName = bindOrgName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
