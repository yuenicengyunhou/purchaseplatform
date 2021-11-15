package com.rails.lib_data.model;

import android.text.TextUtils;

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
import io.reactivex.functions.BiFunction;
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
        Observable banners = getBanners().subscribeOn(Schedulers.io());//获取商城banner列表
        Observable categorys = getCategorys().subscribeOn(Schedulers.io());//获取导航


        Observable.zip(  banners, categorys, (BiFunction< ArrayList<BannerBean>,
                                        ArrayList<NavigationBean>, MarketIndexBean>) (hBanners, hCategorys) -> {
            MarketIndexBean marketIndexBean = new MarketIndexBean();

            try {
                marketIndexBean.setBannerBeans(hBanners);
            } catch (Exception e) {
                marketIndexBean.setBannerBeans(null);
            }

            try {
                if (hCategorys != null) {
                    ArrayList<NavigationBean> navigationBeans = new ArrayList<>();
                    for (NavigationBean bean : hCategorys) {
                        String url = bean.getPictureUrl();
                        if (!TextUtils.isEmpty(url)) {
                            navigationBeans.add(bean);
                        }
                    }
                    marketIndexBean.setCategorySubBeans(navigationBeans);
                }

            } catch (Exception e) {

            }

            return marketIndexBean;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }

}
