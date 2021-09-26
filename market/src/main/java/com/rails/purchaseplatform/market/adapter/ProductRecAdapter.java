package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.graphics.Color;

import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceGirdWeightDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketProductRecBinding;

import androidx.databinding.adapters.Converters;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

/**
 * 商城首页列表adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductRecAdapter extends BaseRecyclerAdapter<ProductRecBean, ItemMarketProductRecBinding> {

    private String[] colors;

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
        try {
//            binding.tvTitle.setTextColor(Color.parseColor(colors[position % 5]));
//            if (position == 0){
//                binding.tvTitle.setKeyRightIcon(R.drawable.ic_hot);
//            }else{
//                binding.tvTitle.removeKeyRightIcon();
//            }
        } catch (Exception e) {
        }

        binding.recycler.setBackgroundColor(Color.parseColor(bean.getLowerRgb()));

        ProductRecSubAdapter adapter = new ProductRecSubAdapter(mContext);
        binding.recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.recycler.setAdapter(adapter);
        adapter.update(bean.getFloorList(), true);

        adapter.setListener(new PositionListener<ProductBean>() {
            @Override
            public void onPosition(ProductBean bean, int position) {
                if (positionListener != null) {
                    positionListener.onPosition(bean, position);
                }
            }
        });

        binding.tvTitle.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(bean, position);
            }
        });

    }


    @Override
    protected void onBindView(ItemMarketProductRecBinding binding) {
        super.onBindView(binding);
        binding.recycler.addItemDecoration(new SpaceGirdWeightDecoration(mContext, 5, 15, 5, 15, R.color.white));
    }
}
