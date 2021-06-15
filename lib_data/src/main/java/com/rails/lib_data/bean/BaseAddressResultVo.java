package com.rails.lib_data.bean;

import com.rails.lib_data.bean.address.BaseAddress;

import java.util.ArrayList;

public class BaseAddressResultVo {


    /**
     * baseAddress : []
     * childNum : 0
     * code : string
     * created : 2021-04-20T02:43:05.081Z
     * id : 0
     * key : 0
     * latitude : 0
     * level : 0
     * longitude : 0
     * modified : 2021-04-20T02:43:05.081Z
     * name : string
     * parentCode : string
     * platformId : 0
     * region : 0
     * status : 0
     * yn : 0
     */

    private int childNum;//子节点数量 ,
    private String code;//县镇市省编码，省、直辖市两位；普通市、直辖（区、县）两位；普通县两位；镇、乡3位；村委会3位；共12位 ,
    private String created;//创建时间 ,
    private int id;
    private int key;
    private int latitude;//纬度 ,
    private int level;// 等级：1省 2市 3县/县级市/区 4镇/街道 5村/社区 ,
    private int longitude;//经度 ,
    private String modified;//更新时间 ,
    private String name;//地区名称 ,
    private String parentCode;// 低级地址id ,
    private int platformId;
    private int region;//区域划分 1：华北 2：华东 3：华中 4：华南 5：东北 6：西北 7：西南 8：港澳台 9：海外 ,
    private int status;//1启用，0停用 ,
    private int yn;//状态：1启用 2已作废
    private ArrayList<BaseAddress> baseAddress;

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public ArrayList<BaseAddress> getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(ArrayList<BaseAddress> baseAddress) {
        this.baseAddress = baseAddress;
    }
}
