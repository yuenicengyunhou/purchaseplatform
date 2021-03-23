package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.rails.lib_data.bean.BrandBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketBrandBinding;

/**
 * 推荐品牌adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/16
 */
public class BrandAdapter extends BaseRecyclerAdapter<BrandBean, ItemMarketBrandBinding> {
    public BrandAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_brand;
    }

    @Override
    protected void onBindItem(ItemMarketBrandBinding binding, BrandBean brandBean, int position) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.imgBrand.getLayoutParams();
        params.width = (ScreenSizeUtil.getScreenWidth(mContext) - ScreenSizeUtil.dp2px(mContext, 62)) >> 2;
        binding.setBrand(brandBean);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null)
                    positionListener.onPosition(brandBean, position);
            }
        });
    }

}
