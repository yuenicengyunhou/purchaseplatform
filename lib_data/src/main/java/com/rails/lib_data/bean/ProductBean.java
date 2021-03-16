package com.rails.lib_data.bean;

/**
 * 商城--产品模型
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductBean {


    /**
     * id : 19748
     * key : 19748
     * platformId : 20
     * pictureUrl : //oss.mall.95306.cn/mall/20210117143712082.jpg
     * pictureName : 金龙鱼双一万稻米油5L（5000）
     * linkUrl : https://mall.95306.cn/mall-view/product/detail?itemId=1002595
     * redirectionStatus : 1
     * foreignId : 3370
     * typeCode : 1
     * sortNumber : 1
     * operatorId : 1000091792
     * operatorName : gtscyy03
     * created : 2021-03-08 15:00:53
     * modified : 2021-03-08 15:00:53
     * yn : 1
     * skuId : 14080
     * itemId : 1002595
     * saleStatus : 1
     * skuName : null
     * skuPrice : null
     * saleNum : null
     */

    private Integer id;
    private Integer key;
    private Integer platformId;
    private String pictureUrl;
    private String pictureName;
    private String linkUrl;
    private Integer redirectionStatus;
    private Integer foreignId;
    private Integer typeCode;
    private Integer sortNumber;
    private Integer operatorId;
    private String operatorName;
    private String created;
    private String modified;
    private Integer yn;
    private Integer skuId;
    private Integer itemId;
    private Integer saleStatus;
    private Object skuName;
    private Object skuPrice;
    private Object saleNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPictureUrl() {
//        return "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg07file.tooopen.com%2Fimages%2F20180829%2Ftooopen_sl_213337333747611.jpg&refer=http%3A%2F%2Fimg07file.tooopen.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617418238&t=04923941d0c4c8035e9df66e028df869";
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getRedirectionStatus() {
        return redirectionStatus;
    }

    public void setRedirectionStatus(Integer redirectionStatus) {
        this.redirectionStatus = redirectionStatus;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Object getSkuName() {
        return skuName;
    }

    public void setSkuName(Object skuName) {
        this.skuName = skuName;
    }

    public Object getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Object skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Object getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Object saleNum) {
        this.saleNum = saleNum;
    }
}
