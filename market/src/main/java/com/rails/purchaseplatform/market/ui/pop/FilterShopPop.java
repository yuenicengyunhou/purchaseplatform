package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.adapter.SearchItemFilterAdapter;
import com.rails.purchaseplatform.market.adapter.ShopFilterAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketShopFilterBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 筛选
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/29
 */
public class FilterShopPop extends BasePop<PopMarketShopFilterBinding> {

    //    final private String TAG = PropertyPop.class.getSimpleName();
    public static final int
            MODE_1 = 1, // 商品详情页选择规格
            MODE_2 = 2, // 商品详情页购物车弹窗
            MODE_3 = 3, // 商品搜索结果页筛选
            MODE_4 = 4;//店铺详情筛选弹窗

    //    private int mSkuId;
    private ArrayList<SearchFilterBean> mBeans;
    private int mMode = 0;
    private FilterCompleteListener filterCompleteListener;
    private ShopFilterAdapter shopAdapter;

    public FilterShopPop() {
        super();
    }


    public FilterShopPop(ArrayList<SearchFilterBean> beans, int mode) {
        super();
        mBeans = beans;
        mMode = mode;
    }

    public void setCompleteListener(FilterCompleteListener completeListener) {
        this.filterCompleteListener = completeListener;
    }

    @Override
    protected void initialize(Bundle bundle) {
        switch (mMode) {
            case MODE_1:
                PropertyAdapter adapter = new PropertyAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter);
                //(ArrayList<SpecificationPopBean>)
                adapter.update(mBeans, true);
//                onClick();
                break;
            case MODE_2:
                // TODO: 2021/4/28 Do nothing.
                break;
            case MODE_3:
                SearchItemFilterAdapter adapter1 = new SearchItemFilterAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(adapter1);
                //(ArrayList<SearchFilterBean>)
                adapter1.update(mBeans, true);
                break;
            case MODE_4:
                shopAdapter = new ShopFilterAdapter(getActivity());
                binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
                binding.recycler.setAdapter(shopAdapter);
                //(ArrayList<SearchFilterBean>)
                shopAdapter.update(mBeans, true);
                onClick();
                break;
            default:
                break;
        }
    }

//    public void setSkuId(int skuId) {
//        this.mSkuId = skuId;
//    }

    void onClick() {
        binding.btnClose.setOnClickListener(v -> dismiss());

//        final String SALE_NUM = "1"; // 固定1
        binding.btnOk.setOnClickListener(v -> {
            if (null != filterCompleteListener && null != shopAdapter) {
                filterCompleteListener.onComplete(shopAdapter.getDataSource());
            }
            dismiss();
        });

        binding.btnReset.setOnClickListener(v -> {
            for (SearchFilterBean filterBean : mBeans) {
                ArrayList<SearchFilterValue> filterValues = filterBean.getFilterValues();
                for (SearchFilterValue value : filterValues) {
                    value.setSelect(false);
                }
            }
            shopAdapter.update(mBeans, true);
        });
    }

    //    public void setAddToCartListener(PropertyPop.AddToCart addToCart) {
//        this.mAddToCart = addToCart;
//    }
//
    public interface FilterCompleteListener {
        void onComplete(ArrayList<SearchFilterBean> filterbeans);

//        void onReset();
    }

}
