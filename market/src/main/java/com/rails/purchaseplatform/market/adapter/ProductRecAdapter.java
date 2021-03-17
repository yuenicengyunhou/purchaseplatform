package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductRecBinding;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 商城首页列表adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductRecAdapter extends BaseRecyclerAdapter<ProductRecBean, ItemMarketProductRecBinding> {
    public ProductRecAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_product_rec;
    }

    @Override
    protected void onBindItem(ItemMarketProductRecBinding binding, ProductRecBean bean, int position) {
        binding.setFloor(bean);
        ProductRecSubAdapter adapter = new ProductRecSubAdapter(mContext);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 0);
        binding.recycler.setAdapter(adapter, ScreenSizeUtil.dp2px(mContext, 42));
        adapter.update(bean.getFloorList(), true);


    }

}
