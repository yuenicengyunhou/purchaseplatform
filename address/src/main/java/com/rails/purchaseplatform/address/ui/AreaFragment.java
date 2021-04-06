package com.rails.purchaseplatform.address.ui;

import com.rails.purchaseplatform.address.adapter.CityAdapter;
import com.rails.purchaseplatform.address.databinding.FragmentAddressAreaBinding;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/6
 */
public class AreaFragment extends LazyFragment<FragmentAddressAreaBinding> implements PositionListener<String> {

    private CityAdapter adapter;
    private AreaListener listener;


    public void setListener(AreaListener listener) {
        this.listener = listener;
    }

    @Override
    protected void loadData() {
        adapter = new CityAdapter(getActivity());
        adapter.setListener(this);

        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.recycler.setAdapter(adapter);


        ArrayList<String> beans = new ArrayList<>();
        beans.add("北京市");
        beans.add("天津市");
        beans.add("石家庄市");
        beans.add("合肥市");
        beans.add("广州市");
        beans.add("上海市");
        beans.add("深圳市");
        adapter.update(beans, true);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onPosition(String bean, int position) {
        if (listener != null) {
            listener.onPosition(bean);
        }
    }


    public interface AreaListener {

        void onPosition(String string);
    }
}
