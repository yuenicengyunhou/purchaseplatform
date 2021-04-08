package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
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

    public InvoicePresenterImpl(Activity mContext, InvoiceContract.InvoiceView invoiceView) {
        super(mContext, invoiceView);
    }

    @Override
    public void getInvoiceContents() {
        ArrayList<InvoiceContentBean> beans = new ArrayList<>();
        beans.add(new InvoiceContentBean("发票类型", "增值税专用发票", 0));
        beans.add(new InvoiceContentBean("发票类型", "企业增值税普通发票", 1));


        ArrayList<InvoiceContentBean> contents = new ArrayList<>();
        contents.add(new InvoiceContentBean("发票内容", "明细", 0));
        contents.add(new InvoiceContentBean("发票内容", "大类", 1));

        if (isCallBack()) {
            baseView.getInvoiceContents(beans, contents);
        }
    }

    @Override
    public void getInvoiceTitles() {

        Type type = new TypeToken<ArrayList<InvoiceTitleBean>>() {
        }.getType();
        ArrayList<InvoiceTitleBean> beans = JsonUtil.parseJson(mContext, "invoice.json", type);
        if (isCallBack()) {
            baseView.getInvoiceTitles(beans);
        }
    }

    @Override
    public void onInvoice() {

    }
}
