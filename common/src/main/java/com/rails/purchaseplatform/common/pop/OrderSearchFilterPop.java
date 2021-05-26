package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.OrderSearchFilterAdapter2;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.databinding.PopOrderSearchFilterBinding;
import com.rails.purchaseplatform.framwork.adapter.listener.CompleteListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购单列表页筛选按钮弹窗
 */
public class OrderSearchFilterPop extends BasePop<PopOrderSearchFilterBinding> {

    private String[] mTexts;
    private CompleteListener<OrderFilterBean> completeListener;
    private ArrayList<SearchFilterBean> mFilterList;

    public void setCompleteListener(CompleteListener<OrderFilterBean> completeListener) {
        this.completeListener = completeListener;
    }

    //    private String[] orderStatus = {"全部", "待下单", "已驳回", "待发货", "已发货", "待收货", "已取消", "已收货", "采购人取消"};
//    private String[] orderStatus = {"全部", "待下单", "已驳回", "待发货", "已发货", "待收货", "已取消", "已收货", "采购人取消"};

    public OrderSearchFilterPop() {
    }

    public OrderSearchFilterPop(String[] text) {
        mTexts = text;
    }

    // TODO 删除无用构造方法
    public OrderSearchFilterPop(String[] text, ArrayList<SearchFilterBean> filterList) {
        mTexts = text;
        mFilterList = filterList;
    }

    @Override
    protected void initialize(Bundle bundle) {

        Type type = new TypeToken<ArrayList<OrderStatusBean>>() {
        }.getType();
        ArrayList<OrderStatusBean> beans = JsonUtil.parseJson(getActivity(), "orderStatus.json", type);

        binding.recyclerStatus.setLayoutManager(new FlowLayoutManager());
        binding.recyclerStatus.addItemDecoration(new SpaceItemDecoration(20));
        OrderSearchFilterAdapter2 mAdapter2 = new OrderSearchFilterAdapter2(getActivity(), beans);
        binding.recyclerStatus.setAdapter(mAdapter2);

        binding.tvReset.setOnClickListener(v -> {
            ToastUtil.show(getActivity(), "重置");
            dismiss();
        });
        binding.tvComplete.setOnClickListener(v -> {
            List<OrderStatusBean> orderStatusBeans = mAdapter2.getmData();
//            String low = binding.etLowPrice.getText().toString().trim();
//            String high = binding.etHighPrice.getText().toString().trim();
            OrderFilterBean filterBean = new OrderFilterBean();
//            filterBean.setLowPrice(low);
//            filterBean.setHighPrice(high);
            filterBean.setStartDate("");
            filterBean.setEndDate("");
            filterBean.setStatusBeans(orderStatusBeans);
            if (null != completeListener) {
                completeListener.onComplete(filterBean);
            }
            dismiss();
        });
        binding.ivClose.setOnClickListener(v -> dismiss());

        setText(mTexts);

    }

    public void setText(String[] texts) {
        if (texts.length > 0) {
            binding.tvStatus.setText(texts[0]);
//            binding.tvPrice.setText(texts[1]);
            binding.tvTime.setText(texts[2]);
        }
    }
}
