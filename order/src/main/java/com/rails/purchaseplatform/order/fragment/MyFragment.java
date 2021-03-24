package com.rails.purchaseplatform.order.fragment;
/*
*采购单列表
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.OrderListAdapter;
import com.rails.purchaseplatform.order.bean.OrderBean;
import com.rails.purchaseplatform.order.bean.PurchaseBean;
import com.rails.purchaseplatform.order.databinding.FragmentMyBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends LazyFragment<FragmentMyBinding> {
    private static int whichFragment;
    private RecyclerView rcy;
    private int mFragmentLabel;
    private OrderListAdapter mAdapter;
    private ArrayList<OrderBean> orderBeans;
    private SmartRefreshLayout smart;
    private ArrayList<PurchaseBean> purchaseBeans;
    private OrderListAdapter mayAdapter;


    public static MyFragment getInstance(int pWhichFragment) {
        whichFragment = pWhichFragment;
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("lable", whichFragment);
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
        View view = inflater.inflate(R.layout.refresh_list_layout, container, false);
        initView(view);
        return view;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    private void initData() {
        for (int i = 0 ;i< 3 ;i++){
            ArrayList<PurchaseBean.Student> students = new ArrayList<>();
            for (int j= 0; j < 2; j++) {
                PurchaseBean.Student s = new PurchaseBean.Student("工作使我快乐", "233.99", "1210118143800008", "152/100盒", "小计：￥599", R.drawable.ic_order_a);
                students.add(s);
            }
            purchaseBeans.add(new PurchaseBean("1210121172900001","2021-01-11  17:29:30","中国铁路某某局集团有限公-admin01",students));
        }
        mayAdapter.notifyDataSetChanged();

    }

    private void initView(View view) {
        rcy = view.findViewById(R.id.recyclerView);
        smart = view.findViewById(R.id.smart);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rcy.setLayoutManager(manager);

        purchaseBeans = new ArrayList<>();
        mayAdapter = new OrderListAdapter( getActivity(),purchaseBeans);
        rcy.setAdapter(mayAdapter);

        initData();
    }
 
    @Override
    protected void loadData() {
        onClick();


    }

    @Override
    protected void loadPreVisitData() {

    }
}