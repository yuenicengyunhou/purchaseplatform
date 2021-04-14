package com.rails.lib_data.request;

public class SearchProductBody {

    /**
     * 平台ID
     */
    private long platformId;

    /**
     * 用户ID
     */
    private long accountId;

    /**
     * 品牌名称列表
     */
    private String[] brands;

    /**
     * 品牌ID列表
     */
    private long[] brandIds;

    /**
     * 类目ID列表
     */
    private long[] cidList;

    /**
     * ID
     */
    private long id;

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 所选类目
     */
    private long cid;

    /**
     * 所选类目类别 1-前台类目 2-后台基础类目
     */
    private int cidType;

    /**
     * 品牌名称列表
     */
    private String brandsString;

    /**
     * 品牌名称
     */
    private String brand;

    /**
     * 类目属性值ID列表
     */
    private String categoryAttrValueIds;

    /**
     * 扩展属性值ID列表
     */
    private String expandAttrValueIds;

    /**
     * 排序字段
     */
    private String orderColumn;

    /**
     * 排序顺序
     */
    private String orderType;

    /**
     * 商品状态
     */
    private int itemStatus;

    /**
     * 商品型号
     */
    private String modelCode;

    /**
     * 店铺ID
     */
    private long shopId;

    /**
     * 店铺ID
     */
    private long shopInfoId;

    /**
     * 搜索方式 1-通过关键字搜索 2-通过类目搜索
     */
    private int conditionType;

    /**
     * 业务类型 1-商城 2-装修
     */
    private int businessType;

    /**
     * 最小价格
     */
    private double minPrice;

    /**
     * 最大价格
     */
    private double maxPrice;

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;


// ============================================================================================== //


    public void setPlatformId(long platformId) {
        this.platformId = platformId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    public void setBrandIds(long[] brandIds) {
        this.brandIds = brandIds;
    }

    public void setCidList(long[] cidList) {
        this.cidList = cidList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setCidType(int cidType) {
        this.cidType = cidType;
    }

    public void setBrandsString(String brandsString) {
        this.brandsString = brandsString;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategoryAttrValueIds(String categoryAttrValueIds) {
        this.categoryAttrValueIds = categoryAttrValueIds;
    }

    public void setExpandAttrValueIds(String expandAttrValueIds) {
        this.expandAttrValueIds = expandAttrValueIds;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public void setShopInfoId(long shopInfoId) {
        this.shopInfoId = shopInfoId;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
