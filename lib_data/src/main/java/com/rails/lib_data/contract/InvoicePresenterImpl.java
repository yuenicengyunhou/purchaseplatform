package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.model.OrderModel;
import com.rails.lib_data.model.OrderVerifyModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 发票相关
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoicePresenterImpl extends BasePresenter<InvoiceContract.InvoiceView> implements InvoiceContract.InvoicePresenter {


    private OrderVerifyModel model;

    public InvoicePresenterImpl(Activity mContext, InvoiceContract.InvoiceView invoiceView) {
        super(mContext, invoiceView);
        model = new OrderVerifyModel();
    }

    @Override
    public void getInvoiceContents() {
        ArrayList<InvoiceContentBean> beans = new ArrayList<>();
        beans.add(new InvoiceContentBean("发票类型", "增值税专用发票", 0));
        beans.add(new InvoiceContentBean("发票类型", "企业增值税普通发票", 1));

        if (isCallBack()) {
            baseView.getInvoiceContents(beans, null);
        }
    }


    @Override
    public void getInvoiceContents(boolean isAll) {
        ArrayList<InvoiceContentBean> contents = new ArrayList<>();
        contents.add(new InvoiceContentBean("发票内容", "明细", 0));
        if (isAll) {
            contents.add(new InvoiceContentBean("发票内容", "大类", 1));
        }

        if (isCallBack()) {
            baseView.getInvoiceContents(null, contents);
        }
    }


    @Override
    public void getInvoiceTitles(boolean isDialog, int page) {

        if (isDialog)
            baseView.showResDialog(R.string.loading);
        model.getInvoiceTitle(page, 20, "2", new HttpRxObserver<ListBeen<InvoiceTitleBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ListBeen<InvoiceTitleBean> listBeen) {
                baseView.dismissDialog();
                baseView.getInvoiceTitles(listBeen);
            }
        });

    }


    @Override
    public void onInvoice() {

    }
}
