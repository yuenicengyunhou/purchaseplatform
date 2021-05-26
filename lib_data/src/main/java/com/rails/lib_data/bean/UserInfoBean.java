package com.rails.lib_data.bean;

import android.text.TextUtils;

/**
 * 用户信息
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/18
 */
public class UserInfoBean {


    private String id;
    private String platformId;
    private Object created;
    private Object modified;
    private Integer yn;
    private Object pid;
    private String synUserId;
    private Object synPid;
    private String accountType;
    private String accountTypeShow;
    private String userName;
    private String accountName;
    private Object password;
    private String mobile;
    private String email;
    private Object avatarPicSrc;
    private String source;
    private String administrator;
    private Object channel;
    private String userStatus;
    private String subAccountType;
    private String legalEntityOrganizationId;
    private String legalEntityOrganizationName;
    private String departmentOrganizationId;
    private String departmentOrganizationName;
    private Object systemAccount;
    private Object systemOrganizationId;
    private Object systemOrganization;
    private String updated;
    private String token;
    private Boolean lay_is_checked;
    private Object shopId;
    private Integer supplierStatus;
    private Object unSocialCrCode;
    private Object roleName;
    private String twoLevelOrgId;
    private String realName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        if (TextUtils.isEmpty(platformId))
            return "20";
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public Object getModified() {
        return modified;
    }

    public void setModified(Object modified) {
        this.modified = modified;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public String getSynUserId() {
        return synUserId;
    }

    public void setSynUserId(String synUserId) {
        this.synUserId = synUserId;
    }

    public Object getSynPid() {
        return synPid;
    }

    public void setSynPid(Object synPid) {
        this.synPid = synPid;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeShow() {
        return accountTypeShow;
    }

    public void setAccountTypeShow(String accountTypeShow) {
        this.accountTypeShow = accountTypeShow;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getAvatarPicSrc() {
        return avatarPicSrc;
    }

    public void setAvatarPicSrc(Object avatarPicSrc) {
        this.avatarPicSrc = avatarPicSrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public Object getChannel() {
        return channel;
    }

    public void setChannel(Object channel) {
        this.channel = channel;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSubAccountType() {
        return subAccountType;
    }

    public void setSubAccountType(String subAccountType) {
        this.subAccountType = subAccountType;
    }

    public String getLegalEntityOrganizationId() {
        return legalEntityOrganizationId;
    }

    public void setLegalEntityOrganizationId(String legalEntityOrganizationId) {
        this.legalEntityOrganizationId = legalEntityOrganizationId;
    }

    public String getLegalEntityOrganizationName() {
        return legalEntityOrganizationName;
    }

    public void setLegalEntityOrganizationName(String legalEntityOrganizationName) {
        this.legalEntityOrganizationName = legalEntityOrganizationName;
    }

    public String getDepartmentOrganizationId() {
        return departmentOrganizationId;
    }

    public void setDepartmentOrganizationId(String departmentOrganizationId) {
        this.departmentOrganizationId = departmentOrganizationId;
    }

    public String getDepartmentOrganizationName() {
        return departmentOrganizationName;
    }

    public void setDepartmentOrganizationName(String departmentOrganizationName) {
        this.departmentOrganizationName = departmentOrganizationName;
    }

    public Object getSystemAccount() {
        return systemAccount;
    }

    public void setSystemAccount(Object systemAccount) {
        this.systemAccount = systemAccount;
    }

    public Object getSystemOrganizationId() {
        return systemOrganizationId;
    }

    public void setSystemOrganizationId(Object systemOrganizationId) {
        this.systemOrganizationId = systemOrganizationId;
    }

    public Object getSystemOrganization() {
        return systemOrganization;
    }

    public void setSystemOrganization(Object systemOrganization) {
        this.systemOrganization = systemOrganization;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getLay_is_checked() {
        return lay_is_checked;
    }

    public void setLay_is_checked(Boolean lay_is_checked) {
        this.lay_is_checked = lay_is_checked;
    }

    public Object getShopId() {
        return shopId;
    }

    public void setShopId(Object shopId) {
        this.shopId = shopId;
    }

    public Integer getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(Integer supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public Object getUnSocialCrCode() {
        return unSocialCrCode;
    }

    public void setUnSocialCrCode(Object unSocialCrCode) {
        this.unSocialCrCode = unSocialCrCode;
    }

    public Object getRoleName() {
        return roleName;
    }

    public void setRoleName(Object roleName) {
        this.roleName = roleName;
    }

    public String getTwoLevelOrgId() {
        return twoLevelOrgId;
    }

    public void setTwoLevelOrgId(String twoLevelOrgId) {
        this.twoLevelOrgId = twoLevelOrgId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
