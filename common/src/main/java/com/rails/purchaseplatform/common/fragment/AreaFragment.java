package com.rails.purchaseplatform.common.fragment;

import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.bean.KeyVelueBean;
import com.rails.lib_data.contract.DistributionAreaContract;
import com.rails.lib_data.contract.DistributionAreaImpl;
import com.rails.purchaseplatform.common.adapter.CityAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.databinding.FragmentAddressAreaBinding;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.bean.BusEvent;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * author： sk_comic@163.com
 * date: 2021/4/6
 */
public class AreaFragment
        extends LazyFragment<FragmentAddressAreaBinding>
        implements PositionListener<AddressArea>,
        DistributionAreaContract.DistributionAreaView {

    DistributionAreaContract.DistributionAreaPresenter presenter;
    private CityAdapter adapter;
    private AreaListener listener;
    private final int type;
    private String code;

    private AreaFragment(int type, String code) {
        this.type = type;
        this.code = code;
    }


    public static AreaFragment getInstance(int type, String code) {
        return new AreaFragment(type, code);
    }


    public void setListener(AreaListener listener) {
        this.listener = listener;
    }

    @Override
    protected void loadData() {
        adapter = new CityAdapter(getActivity());
        adapter.setListener(this);

        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.recycler.setAdapter(adapter);

        presenter = new DistributionAreaImpl(mActivity, this);
        presenter.getDistributionArea(code, false);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    public void onMessageEvent(BusEvent event) {
        super.onMessageEvent(event);
        KeyVelueBean bean = (KeyVelueBean) event.getBean();

        if (type == bean.getKey()) {
            code = bean.getCode();
            presenter.getDistributionArea(bean.getCode(), false);
        }

    }

    @Override
    public void onPosition(AddressArea bean, int position) {
        if (listener != null) {
            listener.onPosition(bean, type);
        }
    }

    /**
     * 获取地区
     */
    @Override
    public void onGetDistributionAreaSuccess(ArrayList<AddressArea> list) {
        adapter.update(list, true);
//        if (type == 0) {
//            adapter.update(list,true);
//        } else if (type == 1) {
//            updateArea();
//        } else {
//            updateTown();
//        }
    }

    public interface AreaListener {

        void onPosition(AddressArea string, int type);
    }
}
