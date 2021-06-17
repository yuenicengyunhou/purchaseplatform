package com.rails.purchaseplatform.user.ui.fragment;

import android.view.View;

import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.user.databinding.FragmentLoginRandomCodeBinding;

import java.util.ArrayList;

public class RandomCodeLoginFragment extends LazyFragment<FragmentLoginRandomCodeBinding> {
    final private String TAG = RandomCodeLoginFragment.class.getSimpleName();

    @Override
    protected void loadData() {
        binding.ibReloadRandomCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/6/15 请求坐标 成功后直接更新到fragment上
            }
        });
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    public ArrayList<String> getLoginInfo() {
        ArrayList<String> infoList = new ArrayList<>();
        infoList.add(binding.etAccountInput.getText().toString().trim());
        infoList.add(binding.etPasswordInput.getText().toString().trim());
        infoList.add(binding.et1.getText().toString().trim());
        infoList.add(binding.et2.getText().toString().trim());
        infoList.add(binding.et3.getText().toString().trim());
        return infoList;
    }

    public void setCoordinateText(String... strings) {
        binding.et1.setText(strings[0]);
        binding.et2.setText(strings[1]);
        binding.et3.setText(strings[2]);
    }
}
