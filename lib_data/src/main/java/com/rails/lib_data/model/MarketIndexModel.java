package com.rails.lib_data.model;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.MarketIndexService;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarketIndexModel {


    public void getMarketIndexInfo() {

        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        Observable<HttpResult<ArrayList<ProductRecBean>>> recProducts;
        recProducts = HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 1).getRecProducts(params));


        Observable<HttpResult<ArrayList<BrandBean>>> brands;
        brands = HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 1).getRecBrands(params));


        HashMap<String, String> bannerParams = new HashMap<>();
        bannerParams.put("platformId", "20");
        bannerParams.put("status", "1");
        Observable<HttpResult<ArrayList<BannerBean>>> banners;
        banners = HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(MarketIndexService.class, 1).getBanners(bannerParams));


        Observable.merge(recProducts,brands,banners)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<HttpResult<? extends ArrayList<?>>>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Nullable
                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }

                    @Override
                    protected void subscribeActual(Observer<? super HttpResult<? extends ArrayList<?>>> observer) {

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpResult<? extends ArrayList<?>> httpResult) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
