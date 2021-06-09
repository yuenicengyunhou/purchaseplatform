package com.rails.purchaseplatform.market.ui.fragment;

import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.market.databinding.FragmentMarketRankBinding;

/**
 * 楼层当个品类排行列表
 * @author： sk_comic@163.com
 * @date: 2021/6/9
 */
public class RankFragment extends LazyFragment<FragmentMarketRankBinding> {

    private String categoryId;

    private RankFragment(String categoryId) {
       this.categoryId = categoryId;
    }

    public static RankFragment getInstance(String categoryId) {
        return new RankFragment(categoryId);

    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }
}
