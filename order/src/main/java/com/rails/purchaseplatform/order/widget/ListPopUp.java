package com.rails.purchaseplatform.order.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.rails.purchaseplatform.common.widget.recycler.LoadMoreRecycler;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.ConditionAdapter;

import java.util.ArrayList;

public class ListPopUp extends PopupWindow {

    public ListPopUp(Context context, ArrayList<String> strings,Activity activity) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_condition, null);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        LoadMoreRecycler recycler = view.findViewById(R.id.recycler);
        ConditionAdapter adapter = new ConditionAdapter(context);
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        adapter.update(strings,true);
//        Loadmo
    }

    public void showPop(View view) {
        showAsDropDown(view);
    }


}
