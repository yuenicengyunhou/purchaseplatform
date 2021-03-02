package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.FragmentCategorySubBinding;

import androidx.fragment.app.Fragment;


/**
 * 分类子列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategorySubFragment extends LazyFragment<FragmentCategorySubBinding> {

    private static final String ARG_PARAM1 = "param1";

    private String name;


    public static CategorySubFragment newInstance(String param1) {
        CategorySubFragment fragment = new CategorySubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        name = getArguments().getString(ARG_PARAM1);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void loadData() {
//        binding.tvName.setText(name);
    }

    @Override
    protected void loadPreVisitData() {

    }
}