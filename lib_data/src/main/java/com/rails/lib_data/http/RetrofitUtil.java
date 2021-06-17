package com.rails.lib_data.http;


import com.rails.lib_data.http.interceptor.CommonInterceptor;
import com.rails.purchaseplatform.framwork.http.observer.BaseRetrofit;

import okhttp3.Interceptor;


/**
 * 网络请求基本配置
 */
public class RetrofitUtil extends BaseRetrofit {

    private static RetrofitUtil retrofitUtil;


    /**
     * 双检索单例模式
     *
     * @return
     */
    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null)
                    retrofitUtil = new RetrofitUtil();
            }
        }
        return retrofitUtil;
    }


    @Override
    public String getBaseUrl() {
        if (!isDebug)
            return HttpConstants.SHOP_URL;
        else
            return HttpConstants.DEBUG_SHOP_URL;
    }

    @Override
    public String getBaseUrl(int type) {
        if (!isDebug) {
            //生产环境
            if (type == 1) {//门户接口
                return HttpConstants.PLATFORM_URL;
            } else if (type == 2)//排行接口
                return HttpConstants.RANK_URL;
            else
                return HttpConstants.PLATFORM_URL;
        } else {
            if (type == 1) {
                return HttpConstants.DEBUG_PLATFORM_URL;
            } else if (type == 2)
                return HttpConstants.DEBUG_RANK_URL;
            else
                return HttpConstants.DEBUG_PLATFORM_URL;
        }
    }

    @Override
    public Interceptor getInterceptor() {
        return new CommonInterceptor();
    }
}
