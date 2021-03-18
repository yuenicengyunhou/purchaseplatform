package com.rails.purchaseplatform.order;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.order.adapter.RecyclerAdapterThree;
import com.rails.purchaseplatform.order.adapter.RecyclerAdapterTwo;
import com.rails.purchaseplatform.order.adapter.RecyclerViewAdapterFour;
import com.rails.purchaseplatform.order.adapter.RecyclerViewAdapterOne;

import java.util.ArrayList;
@Route(path = ConRoute.ORDER.ALL)
public class DeliypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliypage);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("提交订单");
        strings.add("已确定");
        strings.add("已收货");
        RecyclerViewAdapterOne recyclerViewAdapterOne = new RecyclerViewAdapterOne(this);
        recyclerViewAdapterOne.setStrings(strings);
        recyclerView1.setLayoutManager(new GridLayoutManager(this,strings.size()));
        recyclerView1.setAdapter(recyclerViewAdapterOne);

        RecyclerAdapterTwo recyclerAdapterTwo = new RecyclerAdapterTwo(this);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(recyclerAdapterTwo);

        RecyclerAdapterThree recyclerAdapterThree = new RecyclerAdapterThree(this);
        recyclerAdapterThree.setStrings(strings);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setAdapter(recyclerAdapterThree);


        RecyclerView recyclerView4 = findViewById(R.id.recyclerView4);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView4.setLayoutManager(flexboxLayoutManager);
        RecyclerViewAdapterFour recyclerViewAdapterFour = new RecyclerViewAdapterFour(this);
//        ArrayList<String> strings1 = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            strings1.add("jkl"+i);
//        }
//        recyclerViewAdapterFour.setStrings(strings1);
        recyclerView4.setAdapter(recyclerViewAdapterFour);
    }
}