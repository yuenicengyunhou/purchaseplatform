package com.rails.purchaseplatform.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.R;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SearchActivity extends AppCompatActivity {
    private List<String> strings = new ArrayList<>();
//    private SearchDateAdapter searchDateAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initdata();
    }

    private  void initView() {
//        RecyclerView date_rec = findViewById(R.id.date_rec);
//        date_rec.setLayoutManager(new LinearLayoutManager(this));
//        searchDateAdapter1 = new SearchDateAdapter(this);
//        date_rec.setAdapter(searchDateAdapter1);
//        searchDateAdapter1.update(strings, true);
//
//        RecyclerView type_rec = findViewById(R.id.type_rec);
//        type_rec.setLayoutManager(new LinearLayoutManager(this));
//        searchDateAdapter2 = new SearchDateAdapter(this);
//        type_rec.setAdapter(searchDateAdapter2);
//        searchDateAdapter2.update(strings, true);
//
//        RecyclerView maim_body_rec = findViewById(R.id.main_body_rec);
//        maim_body_rec.setLayoutManager(new LinearLayoutManager(this));
//        searchDateAdapter3 = new SearchDateAdapter(this);
//        maim_body_rec.setAdapter(searchDateAdapter3);
//        searchDateAdapter3.update(strings, true);

    }

    private void initdata() {
        strings.add("111111111");
        strings.add("222222222");
        strings.add("333333333");
        strings.add("444444444");
        strings.add("555555555");
        strings.add("666666666");

    }
}