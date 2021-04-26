package com.rails.lib_data.bean.forAppShow;

import java.util.List;


/**
 * 商品的基础属性
 */
public class ItemAttribute {

    /**
     * CID
     */
    private int cid;

    /**
     * 商品ID
     */
    private long itemId;

    /**
     * 商品ID
     */
    private int skuId;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 商品图片 - 缩略图
     */
    private String pictureUrl;

    /**
     * 商品价格
     */
    private double sellPrice;

    /**
     * 弃用的商品价格
     */
    private String deprecatePrice;

    /**
     * 店铺
     */
    private String shopName;

    /**
     * 店铺ID
     */
    private long shopId;

    /**
     * 产品一级分类(固定属性)
     */
    private String type11;

    /**
     * 产品二级分类(固定属性)
     */
    private String type12;

    /**
     * 产品三级分类(固定属性)
     */
    private String type13;

    /**
     * 商品型号(商品一级分类)(固定属性)
     */
    private String type21;

    /**
     * 商品颜色(商品二级分类)(固定属性)
     */
    private String type22;

    /**
     * 商品型号(商品一级分类)(可选属性)
     */
    private List<String> type21s;

    /**
     * 商品颜色(商品二级分类)(可选属性)
     */
    private List<String> type22s;

    /**
     * 商品评分
     */
    private int score;

    /**
     * 销量
     */
    private int sales;

    /**
     * 运费
     */
    private String postPrice;

    /**
     * 其它参数
     */
    private List<String> params;

    /**
     * 需要加载的WebView
     */
    private List<String> webViewUrls;

    /**
     * 产品高清图
     */
    private List<String> photoUrls;

    /**
     * 推荐商品
     */
    private RecommendItemsBean recommendItemsBean;

    /**
     * 用户地址（这个不应该在商品详情Bean中，考虑从用户module中获取）
     */
    private String address;

    /**
     * 购物车商品数量（这个不应该在商品详情Bean中，考虑从购物车module中获取）
     */
    private String cartCounts;


    // ========================================================================================


    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCartCounts() {
        return cartCounts;
    }

    public void setCartCounts(String cartCounts) {
        this.cartCounts = cartCounts;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDeprecatePrice() {
        return deprecatePrice;
    }

    public void setDeprecatePrice(String deprecatePrice) {
        this.deprecatePrice = deprecatePrice;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getType11() {
        return type11;
    }

    public void setType11(String type11) {
        this.type11 = type11;
    }

    public String getType12() {
        return type12;
    }

    public void setType12(String type12) {
        this.type12 = type12;
    }

    public String getType13() {
        return type13;
    }

    public void setType13(String type13) {
        this.type13 = type13;
    }

    public String getType21() {
        return type21;
    }

    public void setType21(String type21) {
        this.type21 = type21;
    }

    public String getType22() {
        return type22;
    }

    public void setType22(String type22) {
        this.type22 = type22;
    }

    public List<String> getType21s() {
        return type21s;
    }

    public void setType21s(List<String> type21s) {
        this.type21s = type21s;
    }

    public List<String> getType22s() {
        return type22s;
    }

    public void setType22s(List<String> type22s) {
        this.type22s = type22s;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(String postPrice) {
        this.postPrice = postPrice;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public List<String> getWebViewUrls() {
        return webViewUrls;
    }

    public void setWebViewUrls(List<String> webViewUrls) {
        this.webViewUrls = webViewUrls;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public RecommendItemsBean getRecommendItemsBean() {
        return recommendItemsBean;
    }

    public void setRecommendItemsBean(RecommendItemsBean recommendItemsBean) {
        this.recommendItemsBean = recommendItemsBean;
    }
}
