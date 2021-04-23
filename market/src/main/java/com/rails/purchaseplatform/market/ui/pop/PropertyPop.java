package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketPropertyBinding;

import java.util.ArrayList;

/**
 * 商品/购物车规格弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class PropertyPop extends BasePop<PopMarketPropertyBinding> {
    final private String TAG = PropertyPop.class.getSimpleName();

    private PropertyAdapter adapter;
    private AddToCart mAddToCart;
    private int mSkuId;

    @Override
    protected void initialize(Bundle bundle) {

        adapter = new PropertyAdapter(getActivity());

        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(adapter);

        ArrayList<String> propertys = new ArrayList<>();
        propertys.add("颜色");
        propertys.add("尺寸");
        propertys.add("颜色");
        propertys.add("尺寸");
        propertys.add("颜色");
        propertys.add("尺寸");
        adapter.update(propertys, true);

        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(v -> dismiss());

        final String SALE_NUM = "1"; // 固定1

        binding.btnOk.setOnClickListener(v -> {
            mAddToCart.addToCart(String.format(
                    "[{\"saleNum\":\"%s\",\"skuId\":\"%s\"}]",
                    SALE_NUM, mSkuId));
        });
    }

    public void setAddToCartListener(AddToCart addToCart) {
        this.mAddToCart = addToCart;
    }

    public interface AddToCart {
        void addToCart(String skuSaleNumJson);
    }

    public void setSkuId(int skuId) {
        this.mSkuId = skuId;
    }
}
