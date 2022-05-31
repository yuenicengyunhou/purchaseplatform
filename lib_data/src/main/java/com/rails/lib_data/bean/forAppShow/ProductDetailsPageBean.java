package com.rails.lib_data.bean.forAppShow;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;

import java.util.ArrayList;

/**
 * 商详页所有需要展示的数据
 * <p>
 * 商详页可能需要运算的数据
 */
public class ProductDetailsPageBean {

    /**
     * 顶部轮播图
     */
    private ArrayList<String> topPictureList;

    /**
     * 在售价格（红色）
     */
    private String sellPrice;

    /**
     * 市场价格（灰色）
     */
    private String marketPrice;

    /**
     * 商品名称（会跟随sku变化而变化，最开始取Item名称，切换sku取Sku名称）
     */
    private String productName;

    /**
     * 商品型号名称（会跟随sku变化而变化）
     */
    private String productAttrName;

    /**
     * 商品评分（会跟随sku变化而变化）
     */
    private double productScore;

    /**
     * 商品销量（会跟随sku变化而变化）
     */
    private String saleCount;

    /**
     * 包装清单
     */
    private ArrayList<ProductDetailsPackingBean> packingList;

    /**
     * 在售价格（红色，设置给{@link #sellPrice}）
     * <p>
     * 市场价格（灰色，设置给{@link #marketPrice}）
     * <p>
     * sku名称（设置给{@link #skuName}）
     * <p>
     * sku图片
     * <p>
     * 评分（设置给{@link #productScore}）
     * <p>
     * 销量（设置给{@link #saleCount}）
     * <p>
     * 包装清单，需要解析并存到{@link #packingList}中
     */
    private ProductPriceBean priceBean;

    /**
     * 商品信息
     */
    private ProductDetailsBean productDetailsBean;

    /**
     * 库存信息
     */
    private SkuStockBean skuStockBean;

    /**
     * sku
     * 进入详情页时默认第一个（会跟随sku变化而变化）
     */
    private ItemSkuInfo currentItemSkuInfo;

    /**
     * cid 品类ID
     */
    private String cid;

    /**
     * skuID
     */
    private String skuId;

    /**
     * 选择型号弹窗
     */
    private ArrayList<SpecificationPopBean> specPopBeanList;

    /**
     * 商品收藏状态
     */
    private boolean isCollected;

    /**
     * 是否有货
     */
    private boolean isInStock;

    /**
     * 购物车内商品数量
     */
    private String cartCount;

    /**
     * 商品型号（会跟随sku变化而变化）
     */
    private String skuName;

    /**
     * 用户地址（默认地址 index = 0）
     */
    private ArrayList<AddressBean> addressList;

    /**
     * 省
     */
    private String provinceCode;

    /**
     * 地区
     */
    private String cityCode;

    /**
     * 市/县
     */
    private String countryCode;

    /**
     * 地址
     */
    private String fullAddress;


    /**
     * 地址ID，地址可能是重复字符串，但地址ID不是。
     */
    private String addressId;


    /**
     * 运费
     */
    private String delivery;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺Logo
     */
    private String shopLogo;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 店铺风险等级
     */
    private String shopSecurity;

    /**
     * 店铺风险等级图标
     */
    private Drawable shopSecurityIcon;

    /**
     * 店铺推荐商品
     */
    private ArrayList<RecommendItemsBean> recommendItemList;

    /**
     * 商品介绍
     */
    private ArrayList<Bitmap> detailsPictureList;

    /**
     * 商品介绍
     */
    private String detailsPictureUrl;

    /**
     * 售后说明
     */
    private ArrayList<ProductServiceBean> serviceList;

    /**
     * 推荐单位
     */
    private ArrayList<ProductServiceBean> companyList;

    /**
     * 专用物资标识：0-普通物资，1-专用物资。
     */
    private String materialType;


    /**
     * sku图片，不带水印
     */
    private ArrayList<String> skuPicList;


    /**
     * sku图片，带水印
     */
    private ArrayList<String> skuMarkPicList;


    // ===========================================================================================


    public ArrayList<String> getTopPictureList() {
        return topPictureList;
    }

    public void setTopPictureList(ArrayList<String> topPictureList) {
        this.topPictureList = topPictureList;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAttrName() {
        return productAttrName;
    }

    public void setProductAttrName(String productAttrName) {
        this.productAttrName = productAttrName;
    }

    public double getProductScore() {
        return productScore;
    }

    public void setProductScore(double productScore) {
        this.productScore = productScore;
    }

    public ArrayList<ProductDetailsPackingBean> getPackingList() {
        return packingList;
    }

    public void setPackingList(ArrayList<ProductDetailsPackingBean> packingList) {
        this.packingList = packingList;
    }

    public ProductPriceBean getPriceBean() {
        return priceBean;
    }

    public void setPriceBean(ProductPriceBean priceBean) {
        this.priceBean = priceBean;
    }

    public ProductDetailsBean getProductDetailsBean() {
        return productDetailsBean;
    }

    public void setProductDetailsBean(ProductDetailsBean productDetailsBean) {
        this.productDetailsBean = productDetailsBean;
    }

    public SkuStockBean getSkuStockBean() {
        return skuStockBean;
    }

    public void setSkuStockBean(SkuStockBean skuStockBean) {
        this.skuStockBean = skuStockBean;
    }

    public ItemSkuInfo getCurrentItemSkuInfo() {
        return currentItemSkuInfo;
    }

    public void setCurrentItemSkuInfo(ItemSkuInfo currentItemSkuInfo) {
        this.currentItemSkuInfo = currentItemSkuInfo;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public ArrayList<SpecificationPopBean> getSpecPopBeanList() {
        return specPopBeanList;
    }

    public void setSpecPopBeanList(ArrayList<SpecificationPopBean> specPopBeanList) {
        this.specPopBeanList = specPopBeanList;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public ArrayList<AddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<AddressBean> addressList) {
        this.addressList = addressList;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopSecurity() {
        return shopSecurity;
    }

    public void setShopSecurity(String shopSecurity) {
        this.shopSecurity = shopSecurity;
    }

    public Drawable getShopSecurityIcon() {
        return shopSecurityIcon;
    }

    public void setShopSecurityIcon(Drawable shopSecurityIcon) {
        this.shopSecurityIcon = shopSecurityIcon;
    }

    public ArrayList<RecommendItemsBean> getRecommendItemList() {
        return recommendItemList;
    }

    public void setRecommendItemList(ArrayList<RecommendItemsBean> recommendItemList) {
        this.recommendItemList = recommendItemList;
    }

    public ArrayList<Bitmap> getDetailsPictureList() {
        return detailsPictureList;
    }

    public void setDetailsPictureList(ArrayList<Bitmap> detailsPictureList) {
        this.detailsPictureList = detailsPictureList;
    }

    public String getDetailsPictureUrl() {
        return detailsPictureUrl;
    }

    public void setDetailsPictureUrl(String detailsPictureUrl) {
        this.detailsPictureUrl = detailsPictureUrl;
    }

    public ArrayList<ProductServiceBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<ProductServiceBean> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<ProductServiceBean> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(ArrayList<ProductServiceBean> companyList) {
        this.companyList = companyList;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public ArrayList<String> getSkuPicList() {
        return skuPicList;
    }

    public void setSkuPicList(ArrayList<String> skuPicList) {
        this.skuPicList = skuPicList;
    }

    public ArrayList<String> getSkuMarkPicList() {
        return skuMarkPicList;
    }

    public void setSkuMarkPicList(ArrayList<String> skuPicMarkList) {
        this.skuMarkPicList = skuPicMarkList;
    }
}
