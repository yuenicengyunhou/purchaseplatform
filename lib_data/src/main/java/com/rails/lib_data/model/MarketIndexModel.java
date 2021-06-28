package com.rails.lib_data.model;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.NavigationBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.MarketIndexService;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarketIndexModel {


    /**
     * 获取热门推荐列表
     *
     * @param page
     * @param pageSize
     * @param httpRxObserver
     */
    public void getHotProducts(int page, String pageSize, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", page);
        params.put("pageSize", pageSize);
        HttpRxObservable.getObservable(RetrofitUtil
                .getInstance()
                .create(MarketIndexService.class, 2)
                .getHotProducts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取热门推荐列表
     */
    private Observable<HttpResult<ListBeen<ProductBean>>> getHotProducts() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", "10");
        return HttpRxObservable.getObservable(RetrofitUtil
                .getInstance()
                .create(MarketIndexService.class, 2)
                .getHotProducts(params));
    }


    /**
     * 获取楼层数据
     *
     * @param httpRxObserver
     */
    public void getRecProducts(HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil
                .getInstance()
                .create(MarketIndexService.class, 2)
                .getRecProducts())
                .subscribe(httpRxObserver);
    }


    /**
     * 获取楼层列表
     *
     * @return
     */
    private Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts() {
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 2).getRecProducts());
    }


    /**
     * 获取单个楼层品类列表
     *
     * @param floorId
     * @param httpRxObserver
     */
    public void getFloorProducts(int page, String pageSize, String floorId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", page);
        params.put("pageSize", pageSize);
        params.put("firstCategoryId", floorId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 2)
                .getFloorProducts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取品牌列表
     *
     * @param httpRxObserver
     */
    public void getRecBrands(int page, String pageSize, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", page);
        params.put("pageSize", pageSize);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 2)
                .getBrands(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取首页品牌列表
     *
     * @return
     */
    private Observable<HttpResult<ListBeen<BrandBean>>> getRecBrands() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 10);
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 2).getBrands(params));
    }


    /**
     * 获取banner图列表
     *
     * @return
     */
    private Observable<HttpResult<ArrayList<BannerBean>>> getBanners() {
        HashMap<String, String> bannerParams = new HashMap<>();
        bannerParams.put("platformId", "20");
        bannerParams.put("status", "1");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class).getBanners(bannerParams));
    }


    /**
     * 获取分类导航列表
     *
     * @return
     */
    private Observable<HttpResult<ArrayList<NavigationBean>>> getCategorys() {
        HashMap<String, String> bannerParams = new HashMap<>();
        bannerParams.put("platformId", "20");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class).getNavigations(bannerParams));
    }


    /**
     * 获取首页信息
     *
     * @param httpRxObserver
     */
    public void getMarketIndexInfo(HttpRxObserver httpRxObserver) {

        // TODO: 2016/9/30 读取缓存数据
        Observable hotProducts = getHotProducts().subscribeOn(Schedulers.io());//获取热销商品
        Observable recProducts = getRecProducts().subscribeOn(Schedulers.io());//获取商城首页推荐商品列表
        Observable brands = getRecBrands().subscribeOn(Schedulers.io());//获取品牌列表

        Observable banners = getBanners().subscribeOn(Schedulers.io());//获取商城banner列表
        Observable categorys = getCategorys().subscribeOn(Schedulers.io());//获取导航


        Observable.zip(hotProducts, recProducts, brands, banners, categorys, (Function5<ListBeen<ProductBean>, ArrayList<ProductRecBean>, ListBeen<BrandBean>, ArrayList<BannerBean>,
                ArrayList<NavigationBean>, MarketIndexBean>) (hotListBeen, products, brandListBeen, hBanners, hCategorys) -> {
            MarketIndexBean marketIndexBean = new MarketIndexBean();

            String[] resourese = new String[]{"#5566DF", "#47ACF1", "#DDA15B", "#4F5468", "#3DC999"};

            ProductRecBean recBean = new ProductRecBean();
            recBean.setFloorList(hotListBeen.getList());
            recBean.setFirstCategoryName("热销商品");
            recBean.setFirstCategoryId("1");
            products.add(0, recBean);
            for (int i = 0; i < products.size(); i++) {
                products.get(i).setColor(resourese[i % 5]);
            }
            marketIndexBean.setRecBeans(products);
            marketIndexBean.setBrandBeans(brandListBeen.getList());
            marketIndexBean.setBannerBeans(hBanners);

            marketIndexBean.setCategorySubBeans(hCategorys);

            return marketIndexBean;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }

}
