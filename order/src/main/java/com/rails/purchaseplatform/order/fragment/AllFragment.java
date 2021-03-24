package com.rails.purchaseplatform.order.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.order.R;

/*
*采购单列表
 */
public class AllFragment extends Fragment {

    private static int whichFragment;
    public AllFragment() {
        // Required empty public constructor
    }
    public static MyFragment getInstance(int pWhichFragment){
        whichFragment = pWhichFragment;
        MyFragment myFragment= new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("lable",whichFragment);
        myFragment.setArguments(bundle);
        return myFragment;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false);
    }
}