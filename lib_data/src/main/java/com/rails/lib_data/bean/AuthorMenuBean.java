package com.rails.lib_data.bean;

import java.util.List;

/**
 * @author： sk_comic@163.com
 * @date: 2021/5/13
 */
public class AuthorMenuBean {


    private String id;
    private String key;
    private String platformId;
    private String type;
    private String name;
    private String parentId;
    private String systemDefault;
    private String url;
    private String level;
    private String owner;
    private String orderNum;
    private String code;
    private String icon;
    private String target;
    private String htmlCode;
    private String checkArr;
    private Boolean disabled;
    private Boolean checked;
    private Boolean edit;
    private List<SubMenusBean> subMenus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSystemDefault() {
        return systemDefault;
    }

    public void setSystemDefault(String systemDefault) {
        this.systemDefault = systemDefault;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public List<SubMenusBean> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SubMenusBean> subMenus) {
        this.subMenus = subMenus;
    }

    public static class SubMenusBean{
        private String id;
        private String key;
        private String platformId;
        private String type;
        private String name;
        private String parentId;
        private String systemDefault;
        private String url;
        private String level;
        private String owner;
        private String orderNum;
        private String code;
        private String icon;
        private String target;
        private String htmlCode;
        private String checkArr;
        private Boolean disabled;
        private Boolean checked;
        private Boolean edit;
        private List<?> subMenus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getSystemDefault() {
            return systemDefault;
        }

        public void setSystemDefault(String systemDefault) {
            this.systemDefault = systemDefault;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getHtmlCode() {
            return htmlCode;
        }

        public void setHtmlCode(String htmlCode) {
            this.htmlCode = htmlCode;
        }

        public String getCheckArr() {
            return checkArr;
        }

        public void setCheckArr(String checkArr) {
            this.checkArr = checkArr;
        }

        public Boolean getDisabled() {
            return disabled;
        }

        public void setDisabled(Boolean disabled) {
            this.disabled = disabled;
        }

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

        public Boolean getEdit() {
            return edit;
        }

        public void setEdit(Boolean edit) {
            this.edit = edit;
        }

        public List<?> getSubMenus() {
            return subMenus;
        }

        public void setSubMenus(List<?> subMenus) {
            this.subMenus = subMenus;
        }
    }
}
