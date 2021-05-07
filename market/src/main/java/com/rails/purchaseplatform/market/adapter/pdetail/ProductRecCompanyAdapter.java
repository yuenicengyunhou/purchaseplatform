package com.rails.purchaseplatform.market.adapter.pdetail;

import android.content.Context;

import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductDetailServiceBinding;

/**
 * 商品详情--推荐企业
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/7
 */
public class ProductRecCompanyAdapter extends BaseRecyclerAdapter<ProductServiceBean, ItemProductDetailServiceBinding> {

    public ProductRecCompanyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_detail_service;
    }

    @Override
    protected void onBindItem(ItemProductDetailServiceBinding binding, ProductServiceBean productServiceBean, int position) {
        binding.setService(productServiceBean);
    }
}
