package com.rails.purchaseplatform.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderBudgetBean;
import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.InvoiceContract;
import com.rails.lib_data.contract.InvoicePresenterImpl;
import com.rails.lib_data.contract.OrderVerifyContract;
import com.rails.lib_data.contract.OrderVerifyPresenterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.lib_data.request.OrderAddressBean;
import com.rails.lib_data.request.OrderInvoiceBean;
import com.rails.lib_data.request.OrderVerifyBody;
import com.rails.lib_data.request.SkuListBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.pop.AlterDialog;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.OrderVerifyAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityOrderVerityBinding;
import com.rails.purchaseplatform.order.pop.CompanyPop;
import com.rails.purchaseplatform.order.pop.GoodsPop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 核实订单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/27
 */
@Route(path = ConRoute.ORDER.ORDER_VERITY)
public class OrderVerityActivity extends ToolbarActivity<ActivityOrderVerityBinding> implements
        OrderVerifyContract.OrderVerifyView, UserToolContract.UserToolView, InvoiceContract.InvoiceView {

    private OrderVerifyAdapter adapter;
    private OrderVerifyContract.OrderVerifyPresenter presenter;
    private InvoiceContract.InvoicePresenter invoicePresenter;
    private UserToolContract.UserToolPresenter toolPresenter;

    //收货地址
    private AddressBean addressBean;
    //发票参数组合
    private OrderInvoiceBean orderInvoiceBean;
    //结算单位
    private OrderPurchaseBean orderPurchaseBean;

    //提交单多个接口汇总集合
    private OrderVerifyBean verifyBean;


    //是否延迟收货 1：延迟收货 0：无延迟收货
    private int receiveType = 0;

    //下单前要获取的token
    private String orderToken = "";


    //1：普通发票   2：增值税发票
    private int invoiceType = 2;

    private double itemPrice, extraPrice;

    //是否有下单权限
    private boolean canOrder;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        addressBean = (AddressBean) extras.getSerializable("address");
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setTitleBar(R.string.order_verify)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);

        orderInvoiceBean = new OrderInvoiceBean();

        adapter = new OrderVerifyAdapter(this);
        barBinding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        barBinding.recycler.setAdapter(adapter);

        //获取用户是否提交采购单权限
        toolPresenter = new UserToolPresenterImpl(this, this);
        toolPresenter.checkPermissions();

        invoicePresenter = new InvoicePresenterImpl(this, this);
        presenter = new OrderVerifyPresenterImpl(this, this);
        presenter.getOrderToken();
        invoicePresenter.getDefInvoiceTitle();
        presenter.getBudget();
        if (addressBean != null) {
            setAddress(addressBean);
            presenter.getVerifyOrder(addressBean.getId());
        }
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getVerifyOrder(OrderVerifyBean bean) {
        if (bean == null)
            return;
        verifyBean = bean;
        orderPurchaseBean = bean.getCompany();
        adapter.update((ArrayList) bean.getCart().getShopList(), true);
        orderInvoiceBean.setInvoiceAddress(bean.getInvoiceAddress());
        setOrderInfo(bean);
    }

    @Override
    public void getOrderToken(String token) {
        orderToken = token;
    }

    @Override
    public void getPurchases(ArrayList<OrderPurchaseBean> purchaseBeans) {

    }

    @Override
    public void getResult(String msg, String data) {
        String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 0}";
        ResultWebBean bean = JsonUtil.parseJson(json, ResultWebBean.class);
        bean.setRightParams(data);
        ARouter.getInstance().build(ConRoute.MARKET.COMMIT_RESULT)
                .withParcelable("bean", bean)
                .navigation();
        finish();
    }

    @Override
    public void getBudget(OrderBudgetBean bean) {
        if (bean != null) {
            extraPrice = bean.getUsedAmount();
            double budget = bean.getBudgetAmount();
            barBinding.rlTotal.setContent(budget >= 0 ? String.valueOf(DecimalUtil.formatDouble(budget)) : "请联系管理员设置");
            barBinding.rlExtra.setContent(extraPrice >= 0 ? String.valueOf(DecimalUtil.formatDouble(extraPrice)) : "请联系管理员设置");
        }
    }


    /**
     * 设置收货地址
     *
     * @param bean
     */
    private void setAddress(AddressBean bean) {
        if (bean == null) {
            barBinding.btnAddress.setVisibility(View.VISIBLE);
            barBinding.llAddress.setVisibility(View.INVISIBLE);
        } else {
            barBinding.btnAddress.setVisibility(View.GONE);
            barBinding.llAddress.setVisibility(View.VISIBLE);

            barBinding.tvArea.setText(String.format(getResources().getString(R.string.order_verify_pct), bean.getProvinceName(), bean.getCityName(), bean.getCountryName()));
            barBinding.tvAddress.setText(bean.getFullAddress());
            barBinding.tvPhone.setText(String.format(getResources().getString(R.string.order_verify_np), bean.getReceiverName(), bean.getMobile()));
        }
    }

    /**
     * 设置确认单其他信息
     */
    private void setOrderInfo(OrderVerifyBean bean) {
        if (bean == null)
            return;

        setReceiveType(receiveType, "");

        if (bean.getCompany() != null)
            barBinding.rlCompay.setKey(bean.getCompany().getFullName());
        else {
            barBinding.rlCompay.setKey("请联系管理员设置结算单位");
            barBinding.rlCompay.removeValueRightIcon();
            barBinding.rlCompay.setEnabled(false);
        }

        if (bean.getInvoiceAddress() != null) {
            barBinding.tvBillAddress.setText(bean.getInvoiceAddress().getFullAddress());
            barBinding.tvBillPhone.setText(bean.getInvoiceAddress().getReceiverName() + "  " + bean.getInvoiceAddress().getMobile());
        }


        barBinding.rlPay.setKey("账期支付");


        if (bean.getCart() != null) {
            try {
                itemPrice = Double.parseDouble(bean.getCart().getPaymentPrice());
            } catch (Exception e) {
                itemPrice = 0;
            }

            barBinding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ", bean.getCart().getPaymentPrice(), "", 18));
            barBinding.tvTotalNum.setText(String.format(getResources().getString(R.string.order_verify_number), bean.getCart().getTotalSkuNum()));
        }

    }


    /**
     * 设置收货方式
     *
     * @param type
     * @param time
     */
    private void setReceiveType(int type, String time) {
        if (type == 0) {
            barBinding.rlGoods.setKey("正常收货");
            barBinding.rlGoods.setContent(" ");
        } else {
            barBinding.rlGoods.setKey("延迟收货");
            barBinding.rlGoods.setContent(time);
        }
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_SEL).withString("type", "1")
                        .withSerializable("bean", addressBean)
                        .navigation(OrderVerityActivity.this, 0);
            }
        });

        barBinding.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_SEL).navigation(OrderVerityActivity.this, 0);
            }
        });

        barBinding.btnCommit.setOnClickListener(v -> {
            if (canOrder) {
                if (extraPrice >= 0 && itemPrice > extraPrice) {
                    showDialog();
                } else
                    commitOrder();
            } else {
                ToastUtil.showCenter(this, getResources().getString(R.string.common_author_null));
            }


        });

        barBinding.rlGoods.setOnClickListener(v -> {
            GoodsPop pop = new GoodsPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.setListener((type, time) -> {
                receiveType = type;
                setReceiveType(type, time);
            });
            pop.show(getSupportFragmentManager(), "goods");
        });

        barBinding.rlCompay.setOnClickListener(v -> {
            CompanyPop pop = new CompanyPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.setListener(companyBean -> {
                orderPurchaseBean = companyBean;
                barBinding.rlCompay.setKey(companyBean.getFullName());
            });
            Bundle bundle = new Bundle();
            bundle.putParcelable("bean", orderPurchaseBean);
            pop.setArguments(bundle);
            pop.show(getSupportFragmentManager(), "company");
        });

        barBinding.rlBill.setOnClickListener(v -> {
            startIntent(this, InvoiceActivity.class, null, 1);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                if (data == null)
                    return;
                AddressBean bean = (AddressBean) data.getExtras().getSerializable("bean");
                addressBean = bean;
                setAddress(bean);
            } else if (requestCode == 1) {
                if (data == null)
                    return;
                orderInvoiceBean = (OrderInvoiceBean) data.getExtras().getSerializable("invoiceBean");
                String name = data.getExtras().getString("invoiceTitle");
                barBinding.rlBill.setKey(name);

                if (orderInvoiceBean != null) {
                    if (orderInvoiceBean.getInvoiceType() == 2) {
                        barBinding.rlBill.setContent("专票");
                    } else {
                        barBinding.rlBill.setContent("普票");
                    }
                }

                if (orderInvoiceBean.getInvoiceAddress() != null) {
                    barBinding.tvBillAddress.setText(orderInvoiceBean.getInvoiceAddress().getFullAddress());
                    barBinding.tvBillPhone.setText(orderInvoiceBean.getInvoiceAddress().getReceiverName() + "  " + orderInvoiceBean.getInvoiceAddress().getMobile());
                }

            }

        }

    }


    private void commitOrder() {
        OrderVerifyBody body = new OrderVerifyBody();

        //组织地址


        OrderAddressBean orderAddressBean = new OrderAddressBean();
        orderAddressBean.setReceiverName(addressBean.getReceiverName());
        orderAddressBean.setMobile(addressBean.getMobile());
        orderAddressBean.setProvinceCode(addressBean.getProvinceCode());
        orderAddressBean.setCityCode(addressBean.getCityCode());
        orderAddressBean.setCountryCode(addressBean.getCountryCode());
        orderAddressBean.setDetailAddress(addressBean.getFullAddress());
        orderAddressBean.setAddress(addressBean.getFullAddress());
        body.setOrderAddress(orderAddressBean);

        //组织商品内容
        List<SkuListBean> skuListBeans = new ArrayList<>();
        if (verifyBean != null) {
            HashMap<String, String> remarks = new HashMap<>();
            SkuListBean skuListBean;
            CartBean cartBean = verifyBean.getCart();
            body.setPlatformId(cartBean.getPlatformId());
            body.setAccountId(cartBean.getUserId());
            body.setOrganizeId(cartBean.getOrganizeId());
            body.setPaymentPrice(cartBean.getPaymentPrice());


            body.setThrough(1);
            body.setSettleType(20);
            //支付方式
            body.setPaymentType(1);
            //
            //是否延迟收货
            body.setDelayFlag(String.valueOf(receiveType));
            body.setDelayReceiveTime(barBinding.rlGoods.getContent());

            //结算方式
            //结算单位ID
            if (orderPurchaseBean != null) {
                body.setAccountingUnitId(orderPurchaseBean.getId());
                body.setAccountingType(String.valueOf(orderPurchaseBean.getAccountingType()));
            }


            //发票
            orderInvoiceBean.getInvoiceAddress().setAddress(orderInvoiceBean.getInvoiceAddress().getFullAddress());
            body.setOrderInvoice(orderInvoiceBean);


            //商品  && 备注
            for (CartShopBean shopBean : verifyBean.getCart().getShopList()) {
                remarks.put(shopBean.getShopId(), shopBean.remark.get());

                for (CartShopProductBean productBean : shopBean.getSkuList()) {
                    skuListBean = new SkuListBean();
                    skuListBean.setItemId(productBean.getItemId());
                    skuListBean.setSkuNum(productBean.getSkuNum());
                    skuListBean.setSkuId(productBean.getSkuId());
                    skuListBean.setSellPrice(productBean.getSellPrice());
                    skuListBean.setItemName(productBean.getItemName());
                    skuListBeans.add(skuListBean);
                }
            }
            body.setSkuList(skuListBeans);
            body.setRemarkMap((JSONObject) JSON.toJSON(remarks));
        }
        String params = JSONObject.toJSON(body).toString();
        Logger.d(params);

        presenter.commitOrder(orderToken, params);
    }


    /**
     * 默认发票内容
     *
     * @param titleBean
     */
    private void setInvoiceParams(InvoiceTitleBean titleBean) {
        //发票id
        if (titleBean == null) {
            ToastUtil.showCenter(this, "请选择发票抬头");
            return;
        }

        if (orderInvoiceBean == null)
            orderInvoiceBean = new OrderInvoiceBean();

        //发票内容,默认 明细
        orderInvoiceBean.setContent(1);
        //发票类型 ，
        orderInvoiceBean.setInvoiceType(invoiceType);

        orderInvoiceBean.setInvoiceTitleId(titleBean.getId());
        //未知
        orderInvoiceBean.setInvoiceModality(2);
    }


    private void showDialog() {
        new AlterDialog.Builder().context(this)
                .title("提示")
                .msg("您所在的组织预算不足，是否继续下单？")
                .leftBtn("继续")
                .rightBtn("取消")
                .setDialogListener(new AlterDialog.DialogListener() {
                    @Override
                    public void onLeft() {
                        commitOrder();
                        dismissDialog();
                    }

                    @Override
                    public void onRight() {
                        dismissDialog();
                    }
                })
                .builder().show();
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {
        canOrder = bean.getSubmitPurchaseOrder();
//        barBinding.btnCommit.setEnabled(bean.getSubmitPurchaseOrder());
    }

    @Override
    public void getAuthor(AuthorBean authorBean) {

    }

    @Override
    public void getInvoiceContents(ArrayList<InvoiceContentBean> types, ArrayList<InvoiceContentBean> contents) {

    }

    @Override
    public void getInvoiceTitles(ListBeen<InvoiceTitleBean> listBeen) {
        if (listBeen != null) {
            ArrayList<InvoiceTitleBean> invoiceTitleBeans = listBeen.getList();
            if (invoiceTitleBeans == null || invoiceTitleBeans.isEmpty()) {
                setInvoiceNull();
                return;
            }


            InvoiceTitleBean bean = invoiceTitleBeans.get(0);
            if (bean != null) {
                barBinding.rlBill.setKey(bean.getInvoiceTitle());
                if (bean.getInvoiceType() == 2) {
                    barBinding.rlBill.setContent("专票");
                } else {
                    barBinding.rlBill.setContent("普票");
                }
                setInvoiceParams(bean);
            }
        } else {
            setInvoiceNull();
        }
    }


    private void setInvoiceNull() {
        barBinding.rlBill.setKey("请联系管理员设置发票抬头");
        barBinding.rlBill.removeValueRightIcon();
        barBinding.rlBill.setEnabled(false);
        barBinding.tvBillAddress.setVisibility(View.GONE);
        barBinding.tvBillPhone.setVisibility(View.GONE);
    }
}
