package com.rails.lib_data.bean;

import java.util.List;

import androidx.databinding.ObservableField;

/**
 * 购物车--商铺信息
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/19
 */
public class CartShopBean {


    /**
     * platformId : 20
     * shopId : 202003030109
     * sellerId : 58955
     * shopName : 国铁商城领域列车清洁纸品专营店
     * shopType : 2
     * shopTypeView : 第三方卖货店铺
     * selected : false
     * subtotalPrice : 2.00
     * selectedSubPrice : 0
     * selectedSkuNum : 0
     * freightPrice : 2.00
     */

    private String platformId;
    private String shopId;
    private String sellerId;
    private String shopName;
    private String shopType;
    private String shopTypeView;
    private Boolean selected;
    private String subtotalPrice;
    private String selectedSubPrice;
    private String selectedSkuNum;
    private String freightPrice;
    private List<CartShopProductBean> skuList;
    public final ObservableField<Boolean> isSel = new ObservableField<>();
    public final ObservableField<String> remark = new ObservableField<>();


    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopTypeView() {
        return shopTypeView;
    }

    public void setShopTypeView(String shopTypeView) {
        this.shopTypeView = shopTypeView;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(String subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public String getSelectedSubPrice() {
        return selectedSubPrice;
    }

    public void setSelectedSubPrice(String selectedSubPrice) {
        this.selectedSubPrice = selectedSubPrice;
    }

    public String getSelectedSkuNum() {
        return selectedSkuNum;
    }

    public void setSelectedSkuNum(String selectedSkuNum) {
        this.selectedSkuNum = selectedSkuNum;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public List<CartShopProductBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<CartShopProductBean> skuList) {
        this.skuList = skuList;
    }
}
