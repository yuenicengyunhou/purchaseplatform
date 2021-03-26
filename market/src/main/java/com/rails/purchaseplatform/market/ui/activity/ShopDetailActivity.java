package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityMarketShopBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 店铺详情
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */
public class ShopDetailActivity extends ToolbarActivity<ActivityMarketShopBinding> {


    private SearchResultRecyclerAdapter adapter;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_shop);

        adapter = new SearchResultRecyclerAdapter(this);

        barBinding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        barBinding.recRecycler.setAdapter(adapter);


        Type type = new TypeToken<ArrayList<SearchResultBean>>() {
        }.getType();

        ArrayList<SearchResultBean> beans = JsonUtil.parseJson(this, "searchResult.json", type);

        adapter.update(beans, true);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
