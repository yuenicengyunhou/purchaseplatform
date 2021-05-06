package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderStatusBean;
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

public class OrderFilterPop extends BasePop<PopOrderSearchFilterBinding> {

    private String[] mTexts;
    private CompleteListener<OrderFilterBean> completeListener;
    private OrderFilterBean filterBean;
    private FilterCheckAdapter mAdapter2;
    private DatePickListener datePickListener;
    private PopDismissListener popDismissListener;
    private String startDate = "";
    private String endDate = "";
    private String dateRange = "";

    public void setPopDismissListener(PopDismissListener popDismissListener) {
        this.popDismissListener = popDismissListener;
    }

    public void setCompleteListener(CompleteListener<OrderFilterBean> completeListener) {
        this.completeListener = completeListener;
    }

    public void setDatePickListener(DatePickListener datePickListener) {
        this.datePickListener = datePickListener;
    }

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
//            if (null != datePickListener) {
//                datePickListener.onDatePick();
//            }
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd) -> {
                        endDate = formattTime(year,monthOfYear,dayOfMonth,true);
                        startDate = formattTime(yearEnd,monthOfYearEnd,dayOfMonthEnd,true);
                        String range = formattTime(year,monthOfYear,dayOfMonth,false) + " 至 " + formattTime(yearEnd,monthOfYearEnd,dayOfMonthEnd,false);
                        binding.tvDateRange.setText(range);
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        });

        setText(mTexts);

    }


    /**
     * 记录用户筛选条件
     */
    private void saveFilterBean() {
        List<OrderStatusBean> orderStatusBeans = mAdapter2.getmData();
        String low = binding.etLowPrice.getText().toString().trim();
        String high = binding.etHighPrice.getText().toString().trim();
        filterBean.setLowPrice(low);
        filterBean.setHighPrice(high);
        filterBean.setStartDate(startDate);
        filterBean.setEndDate(endDate);
        filterBean.setStatusBeans(orderStatusBeans);

    }

    public void setText(String[] texts) {
        if (texts.length > 0) {
            binding.tvStatus.setText(texts[0]);
            binding.tvPrice.setText(texts[1]);
            binding.tvTime.setText(texts[2]);
        }
    }

    /**
     * 设置界面参数
     */
    private void loadData() {

        String lowPrice = filterBean.getLowPrice();
        binding.etLowPrice.setText(lowPrice);

        String highPrice = filterBean.getHighPrice();
        binding.etHighPrice.setText(highPrice);

        binding.tvDateRange.setText(dateRange);

        mAdapter2.update(filterBean.getStatusBeans());
    }


    /**
     * 重置
     */

    private void reset() {
        filterBean.resetFilter();
        loadData();
    }

    private String formattTime(int year,int month,int day,boolean formatt) {
        Calendar calendar = Calendar.getInstance();
        // 设置当前日期和时间
        calendar.set(year,month, day);
        // 格式化字符串
        if (formatt) {
            return TimeUtil.LongtoStringFormat(calendar.getTimeInMillis(), TimeUtil.YMDHMS);
        } else {
            return TimeUtil.LongtoStringFormat(calendar.getTimeInMillis(), TimeUtil.YMD);
        }
    }
//    /**
//     * 设置时间
//     */
//    public void setDateRange(String dateRange, String startFormattTime, String endFormattTime) {
////        binding.tvDateRange.setText(dateRange);
//        this.startDate = startFormattTime;
//        this.endDate = endFormattTime;
//
//    }

    public interface DatePickListener {
        void onDatePick();
    }

    public interface PopDismissListener {
        void dismiss(OrderFilterBean data);
    }


}