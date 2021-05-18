package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.reflect.TypeToken;
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
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                            productBean.isLimit.set(false);
                        }
                        shopBean.setItemIds(buffer.substring(0, buffer.length() - 1));
                        shopBean.isSel.set(isAllChecked(shopBean));
                        double total = shopPrice(shopBean);
                        String freightS = shopBean.getFreightPrice();
                        double freight = 0D;
                        try{
                            if (TextUtils.isEmpty(freightS)){
                                freight = 0;
                            }else
                                freight = Double.parseDouble(freightS);
                        }catch (Exception e){

                        }


                        if (total >= freight) {
                            shopBean.dprice.set(String.valueOf(0));
                            shopBean.isshow.set(false);
                        } else {
                            shopBean.dprice.set(DecimalUtil.formatDouble(freight - total));
                            shopBean.isshow.set(true);
                        }
                    }

                    baseView.getCartInfo(cartBean);
                }
            }
        });
    }


    /**
     * 计算一个店的价格
     *
     * @return
     */
    private double shopPrice(CartShopBean cartShopBean) {
        double total = 0;
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
                if (bean.canSel.get() == null)
                    continue;
                if (bean.isSel.get() == null)
                    continue;
                if (bean.isSel.get() && bean.canSel.get()) {
                    total += bean.num.get() * bean.getSellPrice();
                }
            }
        } catch (Exception e) {
            return total;
        }
        return total;
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
                if (isCallBack())
                    baseView.getSelStatus(1, (Boolean) response);
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
                //单品编码[1111204]已下架或失效,请选择其他商品！
                String msg = e.getMsg();
                if (msg.contains("[") && msg.contains("]")) {
                    int start = msg.lastIndexOf("[");
                    int end = msg.lastIndexOf("]") + 1;
                    String array = msg.substring(start, end);
                    Type type = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    ArrayList<String> limits = JsonUtil.parseJson(array, type);
                    baseView.getLimits(limits, msg);
                } else {
                    baseView.onError(e);
                }


            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.getResult(0, "校验成功");
            }

        });
    }

}
