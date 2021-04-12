package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ItemAddressBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 地址列表adapter
 */
public class AddressAdapter extends BaseRecyclerAdapter<AddressBean, ItemAddressBinding> {

    public AddressAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address;
    }

    @Override
    protected void onBindItem(ItemAddressBinding binding, AddressBean addressBean, int position) {
        binding.setAddress(addressBean);

        binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null)
                    positionListener.onPosition(addressBean, position);
            }
        });

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null)
                    mulPositionListener.onPosition(addressBean, position);
            }
        });
    }


    public ArrayList<AddressBean> getResourse(){
        return mDataSource;
    }


    public void swapData(int fromPos, int toPos) {
        notifyItemMoved(fromPos, toPos);
        Collections.swap(mDataSource, fromPos, toPos);
    }


    /**
     * 获取对象
     *
     * @param position
     * @return
     */
    public AddressBean getBean(int position) {
        if (mDataSource.size() > position)
            return mDataSource.get(position);
        return null;
    }


    /**
     * 变更选中状态
     *
     * @param position
     */
    public void modifyDef(int position) {
        for (int i = 0; i < mDataSource.size(); i++) {
            if (i == position)
                mDataSource.get(i).isSel.set(true);
            else
                mDataSource.get(i).isSel.set(false);
        }

    }

}
