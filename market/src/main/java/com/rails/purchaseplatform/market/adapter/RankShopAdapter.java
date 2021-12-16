package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.BrandBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemRankShopBinding;

/**
 * 品牌店铺排行adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/6/10
 */
public class RankShopAdapter extends BaseRecyclerAdapter<BrandBean, ItemRankShopBinding> {

    public RankShopAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_rank_shop;
    }

    @Override
    protected void onBindItem(ItemRankShopBinding binding, BrandBean brandBean, int position) {
        binding.setBrand(brandBean);
        if (position == 0) {
            binding.imgRank.setImageResource(R.drawable.ic_rank_first);
            binding.tvRank.setVisibility(View.GONE);
            binding.imgRank.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            binding.imgRank.setImageResource(R.drawable.ic_rank_two);
            binding.tvRank.setVisibility(View.GONE);
            binding.imgRank.setVisibility(View.VISIBLE);
        } else if (position == 2) {
            binding.tvRank.setVisibility(View.GONE);
            binding.imgRank.setVisibility(View.VISIBLE);
            binding.imgRank.setImageResource(R.drawable.ic_rank_three);
        } else {
            binding.tvRank.setText(String.valueOf(++position));
            binding.tvRank.setBackground(null);
            binding.tvRank.setVisibility(View.VISIBLE);
            binding.imgRank.setVisibility(View.GONE);
        }


        binding.getRoot().setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(brandBean, 0);
        });
    }
}
