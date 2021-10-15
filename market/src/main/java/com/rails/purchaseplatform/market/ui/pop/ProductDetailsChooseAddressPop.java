package com.rails.purchaseplatform.market.ui.pop;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.common.pop.AreaPop;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PopChooseAddressAdapter;
import com.rails.purchaseplatform.market.databinding.PopProductDetailsChooseAddressBinding;

import java.util.ArrayList;

public class ProductDetailsChooseAddressPop extends BasePop<PopProductDetailsChooseAddressBinding> {

    private Context mContext;
    /**
     * 地址集合
     */
    private ArrayList<AddressBean> mAddresses;
    /**
     * 当前选中的地址
     */
    private String mCurrentAddress;
    private PopChooseAddressAdapter mAdapter;
    private AddressListener listener;

    public ProductDetailsChooseAddressPop(Context context, ArrayList<AddressBean> addresses) {
        super();
        mContext = context;
        mAddresses = addresses;
    }

    public void setListener(AddressListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initialize(Bundle bundle) {

        mAdapter = new PopChooseAddressAdapter(mContext, mCurrentAddress);
        binding.brvAddress.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvAddress.setAdapter(mAdapter);
        mAdapter.setListener(new PositionListener<AddressBean>() {
            @Override
            public void onPosition(AddressBean bean, int position) {
                if (listener != null)
                    listener.getAddrss(bean);
                dismiss();
            }

        });

        mAdapter.update(mAddresses, true);

        binding.tvChooseAddress.setOnClickListener(v -> {
            if (listener != null) {
                listener.getArea("");
            }
            dismiss();
        });

//        binding.tvChooseAddress.setOnClickListener(v -> {
//            // TODO: 2021/4/2 把adapter返回的地址数据返回给activity并更新页面
//        });

        binding.ibClose.setOnClickListener(v -> {

            dismiss();
        });

    }

    // 每次打开pop之前都要设置当前地址以便显示图标
    public void setCurrentAddress(String currentAddress) {
        mCurrentAddress = currentAddress;
    }


    public interface AddressListener {


        void getAddrss(AddressBean bean);


        void getArea(String area);
    }
}
