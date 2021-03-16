package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.model.ProductModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * 商品presenter 中间业务逻辑处理
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductPresenterImpl extends BasePresenter<ProductContract.ProductView> implements ProductContract.ProductPresenter {

    private ProductModel model;

    public ProductPresenterImpl(Activity mContext, ProductContract.ProductView productView) {
        super(mContext, productView);
        model = new ProductModel();
    }

    @Override
    public void getRectProducts(boolean isDialog) {
//        if (isDialog)
//            baseView.showDialog("加载中...");
//
//        model.getRecProducts(new HttpRxObserver<ArrayList<ProductRecBean>>() {
//            @Override
//            protected void onError(ErrorBean e) {
//                baseView.dismissDialog();
//                baseView.onError(e);
//
//            }
//
//            @Override
//            protected void onSuccess(ArrayList<ProductRecBean> beans) {
//                baseView.dismissDialog();
//                baseView.getRecProducts(beans);
//            }
//        });

        Type type = new TypeToken<ArrayList<ProductRecBean>>() {
        }.getType();
        ArrayList<ProductRecBean> beans = JsonUtil.parseJson(mContext, "mall.json", type);
        baseView.getRecProducts(beans);


    }

    @Override
    public void getBanners() {
        Type type = new TypeToken<ArrayList<BannerBean>>() {
        }.getType();
        ArrayList<BannerBean> beans = JsonUtil.parseJson(mContext, "banner.json", type);
        baseView.getBanners(beans);
    }

    @Override
    public void getBrands() {
        Type type = new TypeToken<ArrayList<BrandBean>>() {
        }.getType();
        ArrayList<BrandBean> beans = JsonUtil.parseJson(mContext, "brand.json", type);
        baseView.getBrands(beans);
    }

    @Override
    public void getRecCategorys() {
        ArrayList<CategorySubBean> beans = new ArrayList<>();
        beans.add(new CategorySubBean("电子产品", R.drawable.ic_category_electronic));
        beans.add(new CategorySubBean("办公用品", R.drawable.ic_category_office));
        beans.add(new CategorySubBean("粮油食品", R.drawable.ic_category_food));
        beans.add(new CategorySubBean("通用工具", R.drawable.ic_category_tool));
        beans.add(new CategorySubBean("全部分类", R.drawable.ic_category_all));

        baseView.getRecCategorys(beans);
    }
}
