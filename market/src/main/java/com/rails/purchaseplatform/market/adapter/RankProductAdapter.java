package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemRankProductBinding;

/**
 * 商品排行列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/6/10
 */
public class RankProductAdapter extends BaseRecyclerAdapter<ProductBean, ItemRankProductBinding> {

    public RankProductAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_rank_product;
    }

    @Override
    protected void onBindItem(ItemRankProductBinding binding, ProductBean productBean, int position) {
        binding.setProduct(productBean);
        if (position == 0) {
            binding.tvRank.setBackgroundResource(R.drawable.ic_rank_first);
            binding.tvRank.setText("");
        } else if (position == 1) {
            binding.tvRank.setBackgroundResource(R.drawable.ic_rank_two);
            binding.tvRank.setText("");
        } else if (position == 2) {
            binding.tvRank.setText("");
            binding.tvRank.setBackgroundResource(R.drawable.ic_rank_three);
        } else {
            binding.tvRank.setText(String.valueOf(++position));
            binding.tvRank.setBackground(null);
        }

        String price = DecimalUtil.formatDouble(productBean.getSellPrice());
        binding.tvPrice.setText("¥ "+price);

        binding.getRoot().setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(productBean, 0);
        });
    }
}
