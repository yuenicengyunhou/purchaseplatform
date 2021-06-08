package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.bean.orderdetails.LogisticsInfo;
import com.rails.lib_data.bean.orderdetails.OrderDetails;
import com.rails.lib_data.model.OrderModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {

    private final OrderModel model;
    //    private String accountId = "";
    private String organizeName;
    private String organizationId = "";
    private String platformId;

    public OrderPresenterImpl(Activity mContext, OrderContract.OrderView orderView) {
        super(mContext, orderView);
        model = new OrderModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (null == bean) {
            return;
        }
        organizeName = bean.getDepartmentOrganizationName();
        organizationId = bean.getDepartmentOrganizationId();
        platformId = bean.getPlatformId();
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
        model.getSupplierNames(supplierName, organizeName, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
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


    @Override
    public void getSkuNameList(String skuName) {
        model.getSkuNameList(skuName, organizeName, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
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

    @Override
    public void getBrandList(String brand) {
        model.getBrandList(brand, organizeName, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
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

    @Override
    public void getDelivered(String orderNo) {
        if (null == orderNo) {
            ToastUtil.showCenter(mContext, "获取订单信息失败");
            return;
        }
        baseView.showResDialog(R.string.loading);
        model.getDeliverFiles(platformId, orderNo, new HttpRxObserver<OrderDetails>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(OrderDetails response) {
                baseView.dismissDialog();
                LogisticsInfo logisticsInfo = response.getLogisticsInfo();
                String deliveryFile = logisticsInfo.getDeliveryFile();
                String deliveryFileName = logisticsInfo.getDeliveryFileName();
                Log.e("WQ", "---file==" + deliveryFile + "    name===" + deliveryFileName);
                ArrayList<DeliveredFile> fileList = model.getFileList(deliveryFile, deliveryFileName);
                baseView.loadDeliveredFileList(fileList);

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
        model.getPurchasePageList(queryType, squence, content, page, filterBean, new HttpRxObserver<ListBeen<OrderInfoBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                if (e.getMsg().contains("but was STRING")) {
                    ArrayList<OrderInfoBean> list = new ArrayList<>();
                    baseView.getOrder(list, true, 0);
                } else {
                    baseView.onError(e);
                }
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ListBeen<OrderInfoBean> response) {
                boolean firstPage = response.isFirstPage();
                int totalCount = response.getTotalCount();
                baseView.dismissDialog();
                ArrayList<OrderInfoBean> list = response.getList();
                baseView.getOrder(list, firstPage, totalCount);
            }
        });
    }
}
