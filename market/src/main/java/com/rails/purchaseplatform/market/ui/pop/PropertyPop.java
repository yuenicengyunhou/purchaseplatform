package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

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
    private DoFilter mDoFilter;
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
                binding.llTop.setVisibility(View.VISIBLE);
                PropertyAdapter adapter = new PropertyAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter);
                adapter.update(mBeans, true);
                binding.btnClose.setOnClickListener(v -> this.dismiss());
                if (mMode == MODE_2) {
                    binding.btnOk.setOnClickListener(v -> mAddToCart.addToCart());
                }
                break;

            case MODE_3:
                binding.rlTitle.setVisibility(View.VISIBLE);
                binding.tvTitle.setText("筛选");
                SearchItemFilterAdapter adapter1 = new SearchItemFilterAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter1);
                adapter1.update(mBeans, true);
                binding.ibClose.setOnClickListener(v -> this.dismiss());
                binding.btnReset.setOnClickListener(v -> adapter1.update(mBeans, true));
                binding.btnOk.setOnClickListener(v -> mDoFilter.doFilter(
                        "brand", "cid",
                        "cate", "expand",
                        "min", "max"));
                break;

            default:
                break;
        }
    }

    /**
     * 商品详情页 加入购物车弹窗按钮监听方法
     *
     * @param addToCart 接口实现
     */
    public void setAddToCartListener(AddToCart addToCart) {
        this.mAddToCart = addToCart;
    }

    /**
     * 商品详情页 加入购物车弹窗 却定按钮监听接口
     * 商详页 使用此接口的匿名内部类 并重写addToCart方法
     */
    public interface AddToCart {
        void addToCart();
    }


    /**
     * @param doFilter
     */
    public void setFilterListener(DoFilter doFilter) {
        this.mDoFilter = doFilter;
    }

    /**
     * 搜索结果页
     */
    public interface DoFilter {
        void doFilter(String brand, String cid,
                      String categoryAttr, String expandAttr,
                      String minPrice, String maxPrice);
    }
}
