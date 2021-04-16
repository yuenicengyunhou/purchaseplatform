package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.contract.CartContract;
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

    private PropertyAdapter adapter;
    private CartContract.CartPresenter2 mPresenter;

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

        binding.btnOk.setOnClickListener(v -> {
            mPresenter.addCart(20L, 20L, 40L, 50, "99999", true);
            // TODO: 2021/4/15 调添加购物车接口
            dismiss();
        });
    }
}
