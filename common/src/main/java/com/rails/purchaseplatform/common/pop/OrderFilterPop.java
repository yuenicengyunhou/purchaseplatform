package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.adapter.FilterCheckAdapter;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.databinding.PopOrderSearchFilterBinding;
import com.rails.purchaseplatform.framwork.adapter.listener.CompleteListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.TimeUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class OrderFilterPop extends BasePop<PopOrderSearchFilterBinding> {

    private String[] mTexts;
    private CompleteListener<OrderFilterBean> completeListener;
    private OrderFilterBean filterBean;
    private FilterCheckAdapter mAdapter2;
    //    private DatePickListener datePickListener;
    private PopDismissListener popDismissListener;
    private String startDate = "";//起始时间，formatt之后的
    private String endDate = "";//截止时间，formatt之后的
    private String dateRangeStr = "";//起止日期显示字段
    private String mGoodsType = "0";//0全部  1通用 2专用


    public void setPopDismissListener(PopDismissListener popDismissListener) {
        this.popDismissListener = popDismissListener;
    }

    public void setCompleteListener(CompleteListener<OrderFilterBean> completeListener) {
        this.completeListener = completeListener;
    }

//    public void setDatePickListener(DatePickListener datePickListener) {
//        this.datePickListener = datePickListener;
//    }

    public OrderFilterPop() {
    }

    public OrderFilterPop(String[] text) {
        mTexts = text;
    }

    public OrderFilterPop(String[] text, OrderFilterBean filterBean) {
        this(text);
        if (null == filterBean) {
            this.filterBean = new OrderFilterBean();
        } else {
            this.filterBean = filterBean;
        }

    }

    @Override
    protected void initialize(Bundle bundle) {
//        List<OrderStatusBean> beans;

        binding.recyclerStatus.setLayoutManager(new FlowLayoutManager());
        binding.recyclerStatus.addItemDecoration(new SpaceItemDecoration(20));
        mAdapter2 = new FilterCheckAdapter(getActivity());
        binding.recyclerStatus.setAdapter(mAdapter2);
//        RadioButton rbAll = (RadioButton) binding.radioGroup.getChildAt(0);
//        binding.tvCommon.setSelected(true);
//        rbAll.setChecked(true);
        List<OrderStatusBean> statusBeans = filterBean.getStatusBeans();
        if (null == statusBeans) {
            Type type = new TypeToken<ArrayList<OrderStatusBean>>() {
            }.getType();
            statusBeans = JsonUtil.parseJson(getActivity(), "orderStatus.json", type);
            filterBean = new OrderFilterBean();
            filterBean.setStatusBeans(statusBeans);
            mAdapter2.update(statusBeans);
        } else {
            loadData();
        }
//        binding.tvCommon.setOnClickListener(v -> setTextSelectedState(binding.tvCommon));
//        binding.tvSpecial.setOnClickListener(v -> setTextSelectedState(binding.tvSpecial));


        binding.tvReset.setOnClickListener(v -> {
                    reset();
                    if (null != popDismissListener) {
                        saveFilterBean();
                        popDismissListener.dismiss(filterBean);
                        dismiss();
                    }
                }
        );//重置


        binding.tvComplete.setOnClickListener(v -> {//完成
            if (null != completeListener) {
                saveFilterBean();
                completeListener.onComplete(this.filterBean);
                dismiss();
            }
        });


        binding.ivClose.setOnClickListener(v -> dismiss());//关闭


        binding.tvDateRange.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd) -> {
                        startDate = formattTime(year, monthOfYear, dayOfMonth, true);
                        endDate = formattTime(yearEnd, monthOfYearEnd, dayOfMonthEnd, true);
                        dateRangeStr = formattTime(year, monthOfYear, dayOfMonth, false) + " 至 " + formattTime(yearEnd, monthOfYearEnd, dayOfMonthEnd, false);
                        binding.tvDateRange.setText(dateRangeStr);
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.bg_blue));
            dpd.setStartTitle("开始日期");
            dpd.setEndTitle("结束日期");
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        });

        setText(mTexts);

    }

    /**
     * 物资大类选中
     */
//    private void setTextSelectedState(TextView selectedText) {
//        if (selectedText == binding.tvCommon) {
//            mGoodsType = "0";
//        } else {
//            mGoodsType = "1";
//        }
//        for (int i = 0; i < binding.linearType.getChildCount(); i++) {
//            TextView child = (TextView) binding.linearType.getChildAt(i);
//            child.setSelected(child.getId() == selectedText.getId());
//        }
//
//    }


    /**
     * 记录用户筛选条件
     */
    private void saveFilterBean() {
        List<OrderStatusBean> orderStatusBeans = mAdapter2.getmData();
        filterBean.setStartDate(startDate);
        filterBean.setEndDate(endDate);
        filterBean.setStatusBeans(orderStatusBeans);
        filterBean.setGoodsType(mGoodsType);

    }

    public void setText(String[] texts) {
        if (texts.length > 0) {
            binding.tvStatus.setText(texts[0]);
//            binding.tvPrice.setText(texts[1]);

            binding.tvTime.setText(texts[2]);
        }
    }

    /**
     * 设置界面参数
     */
    private void loadData() {

        binding.tvDateRange.setText(dateRangeStr);

        mAdapter2.update(filterBean.getStatusBeans());
    }


    /**
     * 重置
     */

    private void reset() {
        filterBean.resetFilter();
        loadData();
    }

    private String formattTime(int year, int month, int day, boolean formatt) {
        Calendar calendar = Calendar.getInstance();
        // 设置当前日期和时间
        calendar.set(year, month, day);
        // 格式化字符串
        if (formatt) {
            return TimeUtil.LongtoStringFormat(calendar.getTimeInMillis(), TimeUtil.YMDHMS);
        } else {
            return TimeUtil.LongtoStringFormat(calendar.getTimeInMillis(), TimeUtil.YMD);
        }
    }

    public interface PopDismissListener {
        void dismiss(OrderFilterBean data);
    }


}