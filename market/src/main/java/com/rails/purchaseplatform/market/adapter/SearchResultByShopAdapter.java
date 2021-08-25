package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultByShopBinding;

import java.util.ArrayList;

public class SearchResultByShopAdapter extends BaseRecyclerAdapter<ShopAttribute, ItemSearchResultByShopBinding> {

    private Context mContext;
    private String mMaterialType;

    final private String CREDIT_LEVEL_1 = "A";
    final private String CREDIT_LEVEL_2 = "B";
    final private String CREDIT_LEVEL_3 = "C";
    final private String CREDIT_LEVEL_4 = "D";
    final private String CREDIT_NAME_1 = " ";
    final private String CREDIT_NAME_2 = "风险较低";
    final private String CREDIT_NAME_3 = "风险较高";
    final private String CREDIT_NAME_4 = "风险极高";


    public SearchResultByShopAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public SearchResultByShopAdapter(Context context, String materialType) {
        super(context);
        mContext = context;
        mMaterialType = materialType;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result_by_shop;
    }

    @Override
    protected void onBindItem(ItemSearchResultByShopBinding binding, ShopAttribute shopAttribute, int position) {
        String shopName = shopAttribute.getShopName();
        if (!TextUtils.isEmpty(shopName)) {
            if (shopName.contains("<em>"))
                shopName = shopName.replace("<em>", "");
            if (shopName.contains("</em>"))
                shopName = shopName.replace("</em>", "");
            binding.textView.setVisibility(View.VISIBLE);
            binding.textView.setText(shopName);
        } else {
            binding.textView.setVisibility(View.INVISIBLE);
        }

        String credit = CREDIT_NAME_1;
        if (shopAttribute.getCreditLevel() != null) {
            switch (shopAttribute.getCreditLevel()) {
                case CREDIT_LEVEL_2:
                    credit = CREDIT_NAME_2;
                    break;
                case CREDIT_LEVEL_3:
                    credit = CREDIT_NAME_3;
                    break;
                case CREDIT_LEVEL_4:
                    credit = CREDIT_NAME_4;
                    break;
                default:
                    break;
            }
        }
        switch (credit) {
            case CREDIT_NAME_1:
                binding.ivSecurityLevel.setVisibility(View.INVISIBLE);
                break;
            case CREDIT_NAME_2:
                binding.ivSecurityLevel.setVisibility(View.VISIBLE);
                binding.ivSecurityLevel.setBackground(mContext.getResources().getDrawable(R.drawable.ic_security_b));
                break;
            case CREDIT_NAME_3:
                binding.ivSecurityLevel.setVisibility(View.VISIBLE);
                binding.ivSecurityLevel.setBackground(mContext.getResources().getDrawable(R.drawable.ic_security_c));
                break;
            case CREDIT_NAME_4:
                binding.ivSecurityLevel.setVisibility(View.VISIBLE);
                binding.ivSecurityLevel.setBackground(mContext.getResources().getDrawable(R.drawable.ic_security_d));
                break;
            default:
                break;
        }
        binding.tvCreditLevel.setText(credit);
        Glide.with(mContext)
                .load("https:" + shopAttribute.getShopPicture())
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ratioImage);
        binding.llShop.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("shopInfoId", String.valueOf(shopAttribute.getShopId()));
            ARouter.getInstance().build(ConRoute.MARKET.SHOP_DETAILS).with(bundle).navigation();
        });

        SearchResultByShopProductsAdapter adapter = new SearchResultByShopProductsAdapter(mContext, mMaterialType);
        binding.brvProductRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.brvProductRecycler.setAdapter(adapter);
        adapter.update((ArrayList<ItemAttribute>) shopAttribute.getItems(), true);
    }
}
