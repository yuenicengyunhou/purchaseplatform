package com.rails.lib_data.bean.forNetRequest.productDetails;

import java.util.List;

public class ProductDetailsBean {

    //    private int minPriceSkuId;
//    private int itemId;
    private ItemPublishVo itemPublishVo;
    private List<ItemPictureVo> itemPictureVoList; // todo 12345
    private List<ItemSkuInfo> itemSkuInfoList;
//    private int priceShowType;
//    private String scoreViewName;


    // ===========================================================================================


//    public int getMinPriceSkuId() {
//        return minPriceSkuId;
//    }

//    public void setMinPriceSkuId(int minPriceSkuId) {
//        this.minPriceSkuId = minPriceSkuId;
//    }

//    public int getItemId() {
//        return itemId;
//    }

//    public void setItemId(int itemId) {
//        this.itemId = itemId;
//    }

    public ItemPublishVo getItemPublishVo() {
        return itemPublishVo;
    }

    public void setItemPublishVo(ItemPublishVo itemPublishVo) {
        this.itemPublishVo = itemPublishVo;
    }

    public List<ItemPictureVo> getItemPictureVoList() {
        return itemPictureVoList;
    }

    public void setItemPictureVoList(List<ItemPictureVo> itemPictureVoList) {
        this.itemPictureVoList = itemPictureVoList;
    }

    public List<ItemSkuInfo> getItemSkuInfoList() {
        return itemSkuInfoList;
    }

    public void setItemSkuInfoList(List<ItemSkuInfo> itemSkuInfoList) {
        this.itemSkuInfoList = itemSkuInfoList;
    }

//    public int getPriceShowType() {
//        return priceShowType;
//    }

//    public void setPriceShowType(int priceShowType) {
//        this.priceShowType = priceShowType;
//    }

//    public String getScoreViewName() {
//        return scoreViewName;
//    }

//    public void setScoreViewName(String scoreViewName) {
//        this.scoreViewName = scoreViewName;
//    }
}
