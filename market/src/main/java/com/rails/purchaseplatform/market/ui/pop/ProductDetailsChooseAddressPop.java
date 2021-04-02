package com.rails.purchaseplatform.market.ui.pop;

import android.content.Context;
import android.os.Bundle;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PopChooseAddressAdapter;
import com.rails.purchaseplatform.market.databinding.PopProductDetailsChooseAddressBinding;

import java.util.ArrayList;

public class ProductDetailsChooseAddressPop extends BasePop<PopProductDetailsChooseAddressBinding> {

    private Context mContext;
    private ArrayList<String> mAddresses;
    private PopChooseAddressAdapter mAdapter;

    public ProductDetailsChooseAddressPop(Context context, ArrayList<String> addresses) {
        super();
        mContext = context;
        mAddresses = addresses;
    }

    @Override
    protected void initialize(Bundle bundle) {

//        binding.tvChooseAddress.setOnClickListener(v -> {
//            // TODO: 2021/4/2 把adapter返回的地址数据返回给activity并更新页面
//        });

    }
}
