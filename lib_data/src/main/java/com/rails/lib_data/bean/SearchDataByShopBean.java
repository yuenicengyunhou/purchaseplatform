package com.rails.lib_data.bean;

import java.util.List;
import java.util.Map;

public class SearchDataByShopBean {
    private ShopListBean shopList;
    private String totalCount;

    public ShopListBean getShopList() {
        return shopList;
    }

    public void setShopList(ShopListBean shopList) {
        this.shopList = shopList;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }


    public class ShopListBean {
        private List<ShopBean> resultList;
        private int pageSize;
        private int page;
        private int count;
        private Map customAggMap;
        private Map aggResultMap;

        public List<ShopBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ShopBean> resultList) {
            this.resultList = resultList;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Map getCustomAggMap() {
            return customAggMap;
        }

        public void setCustomAggMap(Map customAggMap) {
            this.customAggMap = customAggMap;
        }

        public Map getAggResultMap() {
            return aggResultMap;
        }

        public void setAggResultMap(Map aggResultMap) {
            this.aggResultMap = aggResultMap;
        }


        public class ShopBean {
            private long shopSaleCount;
            private String supplierName;
            private String shopPicture;
            private List<String> saleArea;
            private String shopName;
            private long platformId;
            private long shopId;
            private int shopType;
            private List<ShopSkuBean> shop_sku;

            public long getShopSaleCount() {
                return shopSaleCount;
            }

            public void setShopSaleCount(long shopSaleCount) {
                this.shopSaleCount = shopSaleCount;
            }

            public String getSupplierName() {
                return supplierName;
            }

            public void setSupplierName(String supplierName) {
                this.supplierName = supplierName;
            }

            public String getShopPicture() {
                return shopPicture;
            }

            public void setShopPicture(String shopPicture) {
                this.shopPicture = shopPicture;
            }

            public List<String> getSaleArea() {
                return saleArea;
            }

            public void setSaleArea(List<String> saleArea) {
                this.saleArea = saleArea;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public long getPlatformId() {
                return platformId;
            }

            public void setPlatformId(long platformId) {
                this.platformId = platformId;
            }

            public long getShopId() {
                return shopId;
            }

            public void setShopId(long shopId) {
                this.shopId = shopId;
            }

            public int getShopType() {
                return shopType;
            }

            public void setShopType(int shopType) {
                this.shopType = shopType;
            }

            public List<ShopSkuBean> getShop_sku() {
                return shop_sku;
            }

            public void setShop_sku(List<ShopSkuBean> shop_sku) {
                this.shop_sku = shop_sku;
            }


            public class ShopSkuBean {
                private String skuName;
                private long itemId;
                private long upTime; // TODO: 2021/04/19 类型存疑
                private double sellPrice;
                private String skuPicture;
                private long skuId;
                private long cid;

                public String getSkuName() {
                    return skuName;
                }

                public void setSkuName(String skuName) {
                    this.skuName = skuName;
                }

                public long getItemId() {
                    return itemId;
                }

                public void setItemId(long itemId) {
                    this.itemId = itemId;
                }

                public long getUpTime() {
                    return upTime;
                }

                public void setUpTime(long upTime) {
                    this.upTime = upTime;
                }

                public double getSellPrice() {
                    return sellPrice;
                }

                public void setSellPrice(double sellPrice) {
                    this.sellPrice = sellPrice;
                }

                public String getSkuPicture() {
                    return skuPicture;
                }

                public void setSkuPicture(String skuPicture) {
                    this.skuPicture = skuPicture;
                }

                public long getSkuId() {
                    return skuId;
                }

                public void setSkuId(long skuId) {
                    this.skuId = skuId;
                }

                public long getCid() {
                    return cid;
                }

                public void setCid(long cid) {
                    this.cid = cid;
                }
            }
        }
    }
}
