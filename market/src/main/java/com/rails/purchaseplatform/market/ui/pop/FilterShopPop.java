package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketShopFilterBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 筛选
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class FilterShopPop extends BasePop<PopMarketShopFilterBinding> {

    private SearchResultRecyclerAdapter adapter;

    @Override
    protected void initialize(Bundle bundle) {

        adapter = new SearchResultRecyclerAdapter(getActivity());

        binding.recycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(adapter);


        Type type = new TypeToken<ArrayList<SearchResultBean>>() {
        }.getType();

        ArrayList<SearchResultBean> beans = JsonUtil.parseJson(getActivity(), "searchResult.json", type);

//        adapter.update(beans, true);

        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(v -> dismiss());
    }


}
