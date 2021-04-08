package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketPropertyBinding;
import com.rails.purchaseplatform.market.databinding.PopMarketShopFilterBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 商品/购物车规格弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class PropertyPop extends BasePop<PopMarketPropertyBinding> {

    private PropertyAdapter adapter;

    @Override
    protected void initialize(Bundle bundle) {

        adapter = new PropertyAdapter(getActivity());

        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(adapter);

        ArrayList<String> propertys = new ArrayList<>();
        propertys.add("颜色");
        propertys.add("尺寸");
        propertys.add("颜色");
        propertys.add("尺寸");
        propertys.add("颜色");
        propertys.add("尺寸");
        adapter.update(propertys, true);

        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
