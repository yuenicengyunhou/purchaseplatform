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
    public void getShopListWithKeywordOnly(long platformId, long accountId, String keyword, boolean isBuy, int pageNum, int pageSize, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        model.getShopListWithKeywordOnly(
                platformId, accountId, keyword, isBuy, pageNum, pageSize,
                new HttpRxObserver<SearchDataByShopBean>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.dismissDialog();
                        baseView.onError(e);
                    }

                    @Override
                    protected void onSuccess(SearchDataByShopBean bean) {

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
                            shopAttribute.setItems(items);
                            shops.add(shopAttribute);
                        }

                        baseView.dismissDialog();
                        baseView.getShopListWithKeywordOnly(shops, true, true);
                    }
                });
    }
}
