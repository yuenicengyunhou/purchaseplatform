package com.rails.lib_data;

public class AddressArea {


    /**
     * id : 1
     * key : 1
     * platformId : 20
     * code : 11
     * parentCode : 0
     * name : 北京市
     * latitude : 39.929985
     * longitude : 116.395645
     * level : 1
     * region : 1
     * status : 1
     * created : null
     * modified : 1571726710000
     * yn : 1
     * childNum : null
     * baseAddress : null
     */

    private long id;
    private int key;
    private int platformId;
    private String code;
    private String parentCode;
    private String name;
    private double latitude;
    private double longitude;
    private int level;
    private int region;
    private int status;
    private Object created;
    private long modified;
    private int yn;
    private Object childNum;
    private Object baseAddress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public Object getChildNum() {
        return childNum;
    }

    public void setChildNum(Object childNum) {
        this.childNum = childNum;
    }

    public Object getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(Object baseAddress) {
        this.baseAddress = baseAddress;
    }
}
