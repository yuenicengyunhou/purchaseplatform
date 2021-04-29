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
public class PropertyPop<T> extends BasePop<PopMarketPropertyBinding> {
    final private String TAG = PropertyPop.class.getSimpleName();
    final private int
            MODE_1 = 1, // 商品详情页选择规格
            MODE_2 = 2, // 商品详情页购物车弹窗
            MODE_3 = 3, // 商品搜索结果页筛选
            MODE_4 = 4;

    private AddToCart mAddToCart;
    private ArrayList<T> mBeans;
    private int mMode = 0;

    public PropertyPop() {
        super();
    }


    public PropertyPop(ArrayList<T> beans, int mode) {
        super();
        mBeans = beans;
        mMode = mode;
    }

    @Override
    protected void initialize(Bundle bundle) {
        switch (mMode) {
            case MODE_1:
            case MODE_2:
                PropertyAdapter adapter = new PropertyAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter);
                adapter.update(mBeans, true);
                // TODO: 2021/4/28 判断MODE的值展示对应的组件
                onClick();
                break;
            case MODE_3:
                SearchItemFilterAdapter adapter1 = new SearchItemFilterAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter1);
                adapter1.update(mBeans, true);
                break;
            default:
                break;
        }
    }

    void onClick() {
        binding.btnClose.setOnClickListener(v -> dismiss());
        binding.btnOk.setOnClickListener(v -> mAddToCart.addToCart());
    }

    public void setAddToCartListener(AddToCart addToCart) {
        this.mAddToCart = addToCart;
    }

    public interface AddToCart {
        void addToCart();
    }
}
