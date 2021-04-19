package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityMarketShopBinding;
import com.rails.purchaseplatform.market.ui.pop.FilterShopPop;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 店铺详情
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/23
 */
@Route(path = ConRoute.MARKET.SHOP_DETAILS)
public class ShopDetailActivity extends ToolbarActivity<ActivityMarketShopBinding> {


    private SearchResultRecyclerAdapter adapter;
    private FilterShopPop filterPop;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_shop);

        barBinding.swipe.setEnableLoadMore(false);

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

    @Override
    protected void onClick() {
        super.onClick();
        barBinding.rbSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(false, true, false, false);
            }
        });

        barBinding.rbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSelected(false, false, true, false);
            }
        });

        barBinding.rbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(true, false, false, false);
            }
        });

        barBinding.rbSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
    }


    /**
     * 设置选中的view
     *
     * @param booleans
     */
    private void setSelected(Boolean... booleans) {
        int len = booleans.length;
        if (len <= 0)
            return;
        barBinding.rbAll.setSelected(booleans[0]);
        barBinding.rbSale.setSelected(booleans[1]);
        barBinding.rbPrice.setSelected(booleans[2]);
        barBinding.rbSel.setSelected(booleans[3]);
    }


    private void showPop() {
        if (filterPop == null) {
            filterPop = new FilterShopPop();
            filterPop.setType(BasePop.MATCH_WRAP);
            filterPop.setGravity(Gravity.BOTTOM);
        }
        filterPop.show(getSupportFragmentManager(), "shop");

    }
}
