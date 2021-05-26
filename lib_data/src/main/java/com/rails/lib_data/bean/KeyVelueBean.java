package com.rails.lib_data.bean;

/**
 * @author： sk_comic@163.com
 * @date: 2021/5/26
 */
public class KeyVelueBean {
    private int key;
    private String code;

    public KeyVelueBean(int key, String code) {
        this.key = key;
        this.code = code;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
