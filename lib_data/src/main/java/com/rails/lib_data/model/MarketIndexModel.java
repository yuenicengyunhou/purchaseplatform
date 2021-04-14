package com.rails.lib_data.model;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.MarketIndexService;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

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
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarketIndexModel {

    /**
     * 获取楼层列表
     *
     * @return
     */
    public Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts() {
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
    public Observable<HttpResult<ArrayList<BrandBean>>> getRecBrands() {
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
    public Observable<HttpResult<ArrayList<BannerBean>>> getBanners() {
        HashMap<String, String> bannerParams = new HashMap<>();
        bannerParams.put("platformId", "20");
        bannerParams.put("status", "1");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 1).getBanners(bannerParams));
    }


    public void getMarketIndexInfo(HttpRxObserver httpRxObserver) {

        Observable recProducts;
        recProducts = getRecProducts().subscribeOn(Schedulers.io());


        Observable brands;
        brands = getRecBrands().subscribeOn(Schedulers.io());

        Observable<HttpResult<ArrayList<BannerBean>>> banners;
        banners = getBanners();


        Observable.zip(recProducts, brands, (BiFunction<ArrayList<ProductRecBean>, ArrayList<BrandBean>, MarketIndexBean>) (products, hBrand) -> {
            MarketIndexBean marketIndexBean = new MarketIndexBean();

            String[] resourese = new String[]{"#5566DF", "#47ACF1", "#DDA15B", "#4F5468", "#3DC999"};
            for (int i = 0; i < products.size(); i++) {
                products.get(i).setColor(resourese[i % 5]);
            }
            marketIndexBean.setRecBeans(products);
            marketIndexBean.setBrandBeans(hBrand);
            return marketIndexBean;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }

}
