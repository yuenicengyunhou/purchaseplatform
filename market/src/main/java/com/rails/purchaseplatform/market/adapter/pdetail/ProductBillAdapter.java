package com.rails.purchaseplatform.market.adapter.pdetail;

import android.content.Context;

import com.rails.lib_data.bean.forAppShow.ProductDetailsPackingBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductDetailBillBinding;

/**
 * 商品详情，清单列表dapdater
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/7
 */
public class ProductBillAdapter extends BaseRecyclerAdapter<ProductDetailsPackingBean, ItemProductDetailBillBinding> {

    public ProductBillAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_detail_bill;
    }

    @Override
    protected void onBindItem(ItemProductDetailBillBinding binding, ProductDetailsPackingBean productBillBean, int position) {
        binding.setBill(productBillBean);
    }
}
