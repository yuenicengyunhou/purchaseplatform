package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.adapter.SearchItemFilterAdapter;
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
    final private int
            MODE_1 = 1, // 商品详情页选择规格
            MODE_2 = 2, // 商品详情页购物车弹窗
            MODE_3 = 3, // 商品搜索结果页筛选
            MODE_4 = 4;

    private AddToCart mAddToCart;
    private int mSkuId;
    private ArrayList mBeans;
    private ArrayList<SearchFilterBean> mFilterBeans;
    private int mMode = 0;

    public PropertyPop() {
        super();
    }


    public PropertyPop(ArrayList beans, int mode) {
        super();
        mBeans = beans;
        mMode = mode;
    }


//    public PropertyPop(ArrayList beans, int mode) {
//        super();
//        mFilterBeans = beans;
//        mMode = mode;
//    }

    @Override
    protected void initialize(Bundle bundle) {
        switch (mMode) {
            case MODE_1:
                PropertyAdapter adapter = new PropertyAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter);
                adapter.update((ArrayList<SpecificationPopBean>) mBeans, true);
                onClick();
                break;
            case MODE_2:
                // TODO: 2021/4/28 Do nothing.
                break;
            case MODE_3:
                SearchItemFilterAdapter adapter1 = new SearchItemFilterAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter1);
                adapter1.update((ArrayList<SearchFilterBean>) mBeans, true);
                break;
            default:
                break;
        }
    }

    public void setSkuId(int skuId) {
        this.mSkuId = skuId;
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
}
