package com.rails.lib_data.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 商城首页信息
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarketIndexBean implements Serializable {

    //推荐产品
    ArrayList<ProductRecBean> recBeans;
    //推荐品牌
    ArrayList<BrandBean> brandBeans;
    //banner列表
    ArrayList<BannerBean> bannerBeans;

    ArrayList<ProductBean> hotBeans;

    ArrayList<NavigationBean> categorySubBeans;


    public ArrayList<ProductRecBean> getRecBeans() {
        return recBeans;
    }

    public void setRecBeans(ArrayList<ProductRecBean> recBeans) {
        this.recBeans = recBeans;
    }

    public ArrayList<BrandBean> getBrandBeans() {
        return brandBeans;
    }

    public void setBrandBeans(ArrayList<BrandBean> brandBeans) {
        this.brandBeans = brandBeans;
    }

    public ArrayList<BannerBean> getBannerBeans() {
        return bannerBeans;
    }

    public void setBannerBeans(ArrayList<BannerBean> bannerBeans) {
        this.bannerBeans = bannerBeans;
    }

    public ArrayList<NavigationBean> getCategorySubBeans() {
        return categorySubBeans;
    }

    public void setCategorySubBeans(ArrayList<NavigationBean> categorySubBeans) {
        this.categorySubBeans = categorySubBeans;
    }

    public ArrayList<ProductBean> getHotBeans() {
        return hotBeans;
    }

    public void setHotBeans(ArrayList<ProductBean> hotBeans) {
        this.hotBeans = hotBeans;
    }
}
