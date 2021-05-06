package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.OrderModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.ArrayList;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {

    private final OrderModel model;
    private String accountId = "";
    private String organizeName;
    private String organizationId = "";

    public OrderPresenterImpl(Activity mContext, OrderContract.OrderView orderView) {
        super(mContext, orderView);
        model = new OrderModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (null == bean) {
            return;
        }
        accountId = bean.getId();
        organizeName = bean.getDepartmentOrganizationName();
        organizationId = bean.getDepartmentOrganizationId();
    }

    /**
     * 采购人 用户名列表
     */
    @Override
    public void getBuyerNameList(String nameLike, String findType) {
        model.getBuyerNames(nameLike, findType, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(ArrayList<BuyerBean> response) {
                baseView.loadConditionNameList(response);
            }
        });
    }

    /**
     * 供应商名称列表
     */
    @Override
    public void getSupplierNameList(String supplierName) {
        model.getSupplierNames(supplierName, accountId, "2", organizeName, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<BuyerBean> response) {
                baseView.loadConditionNameList(response);
            }
        });
    }
    /**
     * 采购单列表数据
     */

    @Override
    public void getOrder(boolean isDialog, int page, int queryType, String squence, String content, OrderFilterBean filterBean) {
        if (isDialog) {
            baseView.showResDialog(R.string.loading);
        }
        model.getPurchasePageList(20, accountId, queryType, 2, squence, content, page,filterBean, new HttpRxObserver<ListBeen<OrderInfoBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ListBeen<OrderInfoBean> response) {
                boolean lastPage = response.isLastPage();
                boolean firstPage = response.isFirstPage();
                int totalPageCount = response.getTotalPageCount();
                baseView.dismissDialog();
                ArrayList<OrderInfoBean> list = response.getList();
//                boolean isClear = page <= 1;
                baseView.getOrder(list, lastPage, firstPage,totalPageCount);
            }
        });
    }
}
