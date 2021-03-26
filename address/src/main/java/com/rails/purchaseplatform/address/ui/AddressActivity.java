package com.rails.purchaseplatform.address.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.AddressAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.utils.RecyclerTouchHelper;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 地址管理列表页面
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_MAIN)
public class AddressActivity extends ToolbarActivity<ActivityAddressBinding> {

    private AddressAdapter addressAdapter;

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar
                .setTitleBar(R.string.address_main)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerTouchHelper(new RecyclerTouchHelper.DragCallBack() {
            @Override
            public void onMove(int fromPosition, int toPosition) {

            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {

            }
        }));
        itemTouchHelper.attachToRecyclerView(barBinding.recycler);
        addressAdapter = new AddressAdapter(this);
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        barBinding.recycler.setAdapter(addressAdapter);


        Type type = new TypeToken<ArrayList<AddressBean>>() {
        }.getType();

        ArrayList<AddressBean> beans = JsonUtil.parseJson(this, "address.json", type);
        addressAdapter.update(beans, true);

    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(AddressAddActivity.class);
            }
        });
    }
}