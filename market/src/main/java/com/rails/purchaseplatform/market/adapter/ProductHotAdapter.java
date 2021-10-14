package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductHotBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 热门产品
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/18
 */
public class ProductHotAdapter extends BaseRecyclerAdapter<ProductBean, ItemMarketProductHotBinding> {

    int type;

    public ProductHotAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_product_hot;
    }

    @Override
    protected void onBindItem(ItemMarketProductHotBinding binding, ProductBean productBean, int position) {

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
    protected void onBindView(ItemMarketProductHotBinding binding) {
        super.onBindView(binding);
        if (type != 0) {
            RecyclerView.LayoutParams linearParams =
                    (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
            linearParams.width = (ScreenSizeUtil.getScreenWidth(mContext) - ScreenSizeUtil.dp2px(mContext, 60)) / 3;
            binding.getRoot().setLayoutParams(linearParams);
        }
    }
}
