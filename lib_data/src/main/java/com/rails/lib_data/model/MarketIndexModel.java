package com.rails.lib_data.model;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.NavigationBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.MarketIndexService;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.NetWorkUtil;

import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarketIndexModel {


    /**
     * 获取楼层数据
     *
     * @param httpRxObserver
     */
    public void getRecProducts(HttpRxObserver httpRxObserver) {
        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil
                .getInstance()
                .create(MarketIndexService.class)
                .getRecProducts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取楼层列表
     *
     * @return
     */
    private Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts() {
        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class).getRecProducts(params));
    }


    /**
     * 获取品牌列表
     *
     * @return
     */
    private Observable<HttpResult<ArrayList<BrandBean>>> getRecBrands() {
        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class).getRecBrands(params));
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
        Observable recProducts = getRecProducts().subscribeOn(Schedulers.io());
        Observable brands = getRecBrands().subscribeOn(Schedulers.io());
        Observable banners = getBanners().subscribeOn(Schedulers.io());
        Observable categorys = getCategorys().subscribeOn(Schedulers.io());


        Observable.zip(recProducts, brands, banners, categorys, (Function4<ArrayList<ProductRecBean>, ArrayList<BrandBean>, ArrayList<BannerBean>,
                ArrayList<NavigationBean>, MarketIndexBean>) (products, hBrands, hBanners, hCategorys) -> {
            MarketIndexBean marketIndexBean = new MarketIndexBean();

            String[] resourese = new String[]{"#5566DF", "#47ACF1", "#DDA15B", "#4F5468", "#3DC999"};
            int[] res = new int[]{R.drawable.ic_category_electronic, R.drawable.ic_category_office, R.drawable.ic_category_food, R.drawable.ic_category_tool, R.drawable.ic_category_goods};

            for (int i = 0; i < products.size(); i++) {
                products.get(i).setColor(resourese[i % 5]);
            }
            marketIndexBean.setRecBeans(products);
            marketIndexBean.setBrandBeans(hBrands);
            marketIndexBean.setBannerBeans(hBanners);

            for (int i = 0; i < hCategorys.size(); i++) {
                hCategorys.get(i).setRes(res[i % 5]);
            }
            marketIndexBean.setCategorySubBeans(hCategorys);

            return marketIndexBean;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }

}
