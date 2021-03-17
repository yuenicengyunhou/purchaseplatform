package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductRecBinding;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductRecSubBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 商城首页，推荐列表子商品
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductRecSubAdapter extends BaseRecyclerAdapter<ProductBean, ItemMarketProductRecSubBinding> {
    public ProductRecSubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_product_rec_sub;
    }

    @Override
    protected void onBindItem(ItemMarketProductRecSubBinding binding, ProductBean productBean, int position) {

        RecyclerView.LayoutParams linearParams =
                (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
        linearParams.width = (ScreenSizeUtil.getScreenWidth(mContext) - ScreenSizeUtil.dp2px(mContext, 42)) / 3;
        binding.getRoot().setLayoutParams(linearParams);

        binding.setProduct(productBean);
    }

}
