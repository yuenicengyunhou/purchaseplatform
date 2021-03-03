package com.rails.lib_data.bean;

/**
 * 三级分类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategorySubBean {

    private String name;
    private String url;

    public CategorySubBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
