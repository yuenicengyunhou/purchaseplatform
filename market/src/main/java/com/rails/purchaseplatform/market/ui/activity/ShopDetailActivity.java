package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.view.Gravity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.ShopVO;
import com.rails.lib_data.bean.showOnApp.BaseItemAttribute;
import com.rails.lib_data.contract.ShopContract;
import com.rails.lib_data.contract.ShopPresenterImp;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityMarketShopBinding;
import com.rails.purchaseplatform.market.ui.pop.FilterShopPop;

import java.text.MessageFormat;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 店铺详情
 *
 * author： sk_comic@163.com
 * date: 2021/3/23
 */
@Route(path = ConRoute.MARKET.SHOP_DETAILS)
public class ShopDetailActivity extends ToolbarActivity<ActivityMarketShopBinding> implements ShopContract.ShopView {

    private SearchResultRecyclerAdapter adapter;
    private FilterShopPop filterPop;
    private ShopPresenterImp presenter;
//    private long shopInfoId;


    @Override
    protected void initialize(Bundle bundle) {
        Long shopInfoId = PrefrenceUtil.getInstance(this).getLong("shopInfoId", 0);
        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_shop);

        barBinding.swipe.setEnableLoadMore(false);

        adapter = new SearchResultRecyclerAdapter(this);

        barBinding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        barBinding.recRecycler.setAdapter(adapter);


//        Type type = new TypeToken<ArrayList<SearchResultBean>>() {
//        }.getType();
//
//        ArrayList<SearchResultBean> beans = JsonUtil.parseJson(this, "searchResult.json", type);

//        adapter.update(beans, true);

        presenter = new ShopPresenterImp(this, this);
        presenter.getShopDetails(shopInfoId);

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
        barBinding.rbSale.setOnClickListener(v -> setSelected(false, true, false, false));

        barBinding.rbPrice.setOnCheckedChangeListener((buttonView, isChecked) -> setSelected(false, false, true, false));

        barBinding.rbAll.setOnClickListener(v -> setSelected(true, false, false, false));

        barBinding.rbSel.setOnClickListener(v -> showPop());
    }


    /**
     * 设置选中的view
     *
     * param booleans
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


    @Override
    public void loadShopInfo(ShopVO shop) {
        barBinding.tvName.setText(shop.getShopName());
        barBinding.tvPhone.setText(MessageFormat.format("联系电话：{0}", shop.getMobile()));
        barBinding.tvLevel.setText("");
    }

    @Override
    public void loadShopProductList(ArrayList<BaseItemAttribute> list) {
        adapter.update(list,true);
    }
}
