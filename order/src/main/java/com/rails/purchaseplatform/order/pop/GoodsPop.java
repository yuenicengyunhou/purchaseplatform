package com.rails.purchaseplatform.order.pop;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.order.databinding.ItemOrderRecyclerTitleBinding;
import com.rails.purchaseplatform.order.databinding.PopVerifyGoodsBinding;

import java.util.Calendar;

import androidx.annotation.NonNull;

/**
 * 收货方式弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class GoodsPop extends BasePop<PopVerifyGoodsBinding> {


    @Override
    protected void initialize(Bundle bundle) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    Calendar calendar = Calendar.getInstance();
                    // 设置当前日期和时间
                    calendar.set(datePicker.getYear(), datePicker.getMonth(),
                            datePicker.getDayOfMonth());
                    // 格式化字符串
//                    tvTitle.setText(TimeUtil.LongtoStringFormat(calendar.getTimeInMillis(), TimeUtil.MDHM));
                }
            });
        } else {
            binding.datePicker.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                    Calendar calendar = Calendar.getInstance();
                    // 设置当前日期和时间
                    calendar.set(binding.datePicker.getYear(), binding.datePicker.getMonth(),
                            binding.datePicker.getDayOfMonth());
                    // 格式化字符串
//                    tvTitle.setText(TimeUtil.LongtoStringFormat(calendar.getTimeInMillis(), TimeUtil.MDHM));
                }
            });
        }

        selectButton(true, false);

        onClick();
    }


    /**
     * 设置按钮状态
     *
     * @param booleans
     */
    private void selectButton(Boolean... booleans) {
        if (booleans.length < 2)
            return;
        binding.btnNormal.setSelected(booleans[0]);
        binding.btnDelay.setSelected(booleans[1]);
    }


    void onClick() {
        binding.btnClose.setOnClickListener(v -> {
            dismiss();
        });

        binding.btnDelay.setOnClickListener(v -> {
            binding.datePicker.setVisibility(View.VISIBLE);
            selectButton(false, true);
        });


        binding.btnNormal.setOnClickListener(v -> {
            binding.datePicker.setVisibility(View.INVISIBLE);
            selectButton(true, false);
        });

        binding.btnOk.setOnClickListener(v -> {
            dismiss();
        });
    }
}
