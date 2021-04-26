package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.CartModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 购物车架构
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/12
 */
public class CartPresenterImpl extends BasePresenter<CartContract.CartView> implements CartContract.CartPresenter {

    CartModel model;


    public CartPresenterImpl(Activity mContext, CartContract.CartView cartView) {
        super(mContext, cartView);
        model = new CartModel();
    }

    @Override
    public void getCarts(boolean isDialog, String addressId) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        model.getCarts(addressId, new HttpRxObserver<CartBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(CartBean cartBean) {
                baseView.dismissDialog();
                if (isCallBack()) {
                    CartShopBean bean = new CartShopBean();
                    for (CartShopBean shopBean : cartBean.getShopList()) {
                        StringBuffer buffer = new StringBuffer();
                        for (CartShopProductBean productBean : shopBean.getSkuList()) {
                            productBean.num.set(productBean.getSkuNum());
                            productBean.isSel.set(productBean.getSelected());
                            productBean.isCollect.set(productBean.getCollect());

                            buffer.append(productBean.getSkuId() + ",");

                            productBean.canSel.set(productBean.getSaleStatus() == 1);
                            productBean.canAdd.set(true);

                            if (productBean.num.get() <= 1) {
                                productBean.canReduce.set(false);
                            } else
                                productBean.canReduce.set(true);

                            productBean.isLimit.set(productBean.getLimit());
                        }
                        shopBean.setItemIds(buffer.substring(0, buffer.length() - 1));
                        shopBean.isSel.set(isAllChecked(shopBean));
                    }

                    baseView.getCartInfo(cartBean);
                }

            }

        });
    }


    /**
     * 商店商品是否全部被选中
     */
    private boolean isAllChecked(CartShopBean cartShopBean) {
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
                if (bean.isSel == null)
                    return false;
                if (!bean.isSel.get()) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public void addProduct(CartShopProductBean bean, long num) {
        if (bean == null)
            return;
        num++;

        baseView.showResDialog(R.string.loading);
        long finalNum = num;
        model.modifyProduct(bean.getShopId(), "", bean.getSkuId(), bean.getRecommendOrgId(), num, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                baseView.getProjectNumber(finalNum);
            }
        });
    }

    @Override
    public void reduceProduct(CartShopProductBean bean, long num) {
        if (bean == null)
            return;
        if (num < 1)
            return;
        num--;
        baseView.showResDialog(R.string.loading);
        long finalNum = num;
        model.modifyProduct(bean.getShopId(), "", bean.getSkuId(), bean.getRecommendOrgId(), num, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getProjectNumber(finalNum);
            }
        });


    }

    @Override
    public void editProduct(CartShopProductBean bean, long num) {
        if (bean == null)
            return;
        baseView.showResDialog(R.string.loading);
        model.modifyProduct(bean.getShopId(), "", bean.getSkuId(), bean.getRecommendOrgId(), num, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getProjectNumber(num);
            }
        });
    }


    @Override
    public void delProduct(HashMap<String, ArrayList<String>> map, int position) {
        model.delCart(map, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getResult(1, position, "删除成功");
            }
        });

    }

    @Override
    public void collectProduct(String id, int position) {
        baseView.getResult(1, "收藏成功");
    }

    @Override
    public void modifySel(CartShopProductBean bean) {
        if (bean == null)
            return;
        baseView.showResDialog(R.string.loading);
        model.modifySelect(bean.getShopId(), bean.getSkuId(), bean.isSel.get(), new HttpRxObserver() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();

            }

            @Override
            protected void onSuccess(Object response) {
                baseView.dismissDialog();
            }
        });
    }

    @Override
    public void modifyShopSel(String shopId, String skuIds, boolean isSel) {
        baseView.showResDialog(R.string.loading);
        model.modifySelect(shopId, skuIds, isSel, new HttpRxObserver() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
                if (isCallBack())
                    baseView.getSelStatus(1, isSel);

            }

            @Override
            protected void onSuccess(Object response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getSelStatus(1, isSel);
            }
        });

    }

    @Override
    public void modifySelAll(boolean isSel) {

        baseView.showResDialog(R.string.loading);
        model.modifyAllSelect(isSel, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getSelStatus(2, !isSel);
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getSelStatus(2, isSel);
            }
        });
    }

    @Override
    public void verifyCart(String addressId) {
        baseView.showResDialog(R.string.loading);
        model.verifyCart(addressId, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.getResult(0, "校验成功");
            }

        });
    }

}
