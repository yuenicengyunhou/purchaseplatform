package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductHotBinding;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductHotIndexBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 热门产品
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/18
 */
public class ProductHotIndexAdapter extends BaseRecyclerAdapter<ProductBean, ItemMarketProductHotIndexBinding> {


    public ProductHotIndexAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_product_hot_index;
    }

    @Override
    protected void onBindItem(ItemMarketProductHotIndexBinding binding, ProductBean productBean, int position) {

        binding.setProduct(productBean);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null) {
                    positionListener.onPosition(productBean, position);
                }
            }
        });
    }

    @Override
    protected void onBindView(ItemMarketProductHotIndexBinding binding) {
        super.onBindView(binding);
        RecyclerView.LayoutParams linearParams =
                (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
        linearParams.width = (ScreenSizeUtil.getScreenWidth(mContext) - ScreenSizeUtil.dp2px(mContext, 60)) / 3;
        binding.getRoot().setLayoutParams(linearParams);
    }
}
