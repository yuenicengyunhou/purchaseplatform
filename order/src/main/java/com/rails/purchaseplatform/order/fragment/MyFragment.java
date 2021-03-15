package com.rails.purchaseplatform.order.fragment;
/*
*采购单列表
 */
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.MyBuyAdapter;
import com.rails.purchaseplatform.order.bean.OrderBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {
    private static int whichFragment;
    private RecyclerView rcy;
    private int mFragmentLabel;
    private MyBuyAdapter mAdapter;
    private ArrayList<OrderBean> orderBeans;
    private SmartRefreshLayout smart;


    public static MyFragment getInstance(int pWhichFragment){
        whichFragment = pWhichFragment;
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("lable",whichFragment);
        myFragment.setArguments(bundle);
        return myFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentLabel = getArguments().getInt("lable");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }
    private void initData() {
        OrderBean bean=null;
        ArrayList<OrderBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bean=new OrderBean("12123021543312","2021.3.10","铁路学院","这是一个啥东西","256.23","121","152/100盒","100",R.drawable.a);
            list.add(bean);
        }
        orderBeans.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        rcy = view.findViewById(R.id.recyclerView);
        smart = view.findViewById(R.id.smart);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rcy.setLayoutManager(manager);

        orderBeans = new ArrayList<>();
        mAdapter = new MyBuyAdapter(orderBeans,getActivity());
        rcy.setAdapter(mAdapter);

        initData();
    }
}