package com.rails.purchaseplatform.market.adapter.pdetail;

import android.content.Context;

import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductDetailImgBinding;


/**
 * 求助详情imageAdapter
 * author:wangchao
 */
public class DetailImgAdapter extends BaseRecyclerAdapter<ItemPicture, ItemProductDetailImgBinding> {


    public DetailImgAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_detail_img;
    }

    @Override
    protected void onBindItem(ItemProductDetailImgBinding binding, ItemPicture s, int position) {
        binding.setItem(s);
    }

}
