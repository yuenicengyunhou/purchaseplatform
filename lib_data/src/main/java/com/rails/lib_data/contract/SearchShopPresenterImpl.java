package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.lib_data.bean.forNetRequest.searchResult.byShop.SearchDataByShopBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.byShop.ShopBean;
import com.rails.lib_data.bean.forNetRequest.searchResult.byShop.ShopSkuBean;
import com.rails.lib_data.model.SearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

/**
 * 搜索店铺列表结果页面
 */
public class SearchShopPresenterImpl extends BasePresenter<SearchContract.SearchShopView>
        implements
        SearchContract.SearchShopPresenter {


    final private String TAG = SearchItemPresenterImpl.class.getSimpleName();

    private SearchModel model;

    public SearchShopPresenterImpl(Activity mContext, SearchContract.SearchShopView searchShopView) {
        super(mContext, searchShopView);
        model = new SearchModel();
    }


    @Override
    public void getShopListWithKeywordOnly(String materialType, String isBought, int pageNum, int pageSize, String keyword, String orderColumn, String orderType, String shopType, String saleArea, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        model.getShopListWithKeywordOnly(materialType, isBought, pageNum, pageSize, keyword, orderColumn, orderType, shopType, saleArea,
                new HttpRxObserver<SearchDataByShopBean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.dismissDialog();
                        baseView.onError(e);
                    }

                    @Override
                    protected void onSuccess(SearchDataByShopBean bean) {
                        baseView.dismissDialog();
                        if (isCallBack()) {
                            ArrayList<ShopAttribute> shops = new ArrayList<>();
                            for (ShopBean shopBean : bean.getShopList().getResultList()) {

                                ArrayList<ItemAttribute> items = new ArrayList<>();
                                for (ShopSkuBean itemBean : shopBean.getShop_sku()) {
                                    ItemAttribute item = new ItemAttribute();
                                    item.setSkuName(itemBean.getSkuName());
                                    item.setSellPrice(itemBean.getSellPrice());
                                    item.setShopId(shopBean.getShopId());
                                    item.setCid(itemBean.getCid());
                                    item.setItemId(itemBean.getItemId());
                                    item.setShopName(shopBean.getShopName());
                                    item.setSkuId(itemBean.getSkuId());
                                    item.setPictureUrl(itemBean.getSkuPicture());
                                    items.add(item);
                                }
                                ShopAttribute shopAttribute = new ShopAttribute();
                                shopAttribute.setShopName(shopBean.getShopName());
                                shopAttribute.setShopId(shopBean.getShopId());
                                shopAttribute.setShopPicture(shopBean.getShopPicture());
                                shopAttribute.setCreditLevel(shopBean.getCreditLevel());
                                shopAttribute.setRate(shopBean.getRate());
                                shopAttribute.setTotalPoints(shopBean.getTotalPoints());
                                shopAttribute.setItems(items);
                                shops.add(shopAttribute);
                            }
                            baseView.getShopListWithKeywordOnly(shops, true, pageNum <= 1);
                        }

                    }
                });
    }
}
