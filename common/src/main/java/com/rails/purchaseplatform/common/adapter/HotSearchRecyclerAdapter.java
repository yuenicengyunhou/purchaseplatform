package com.rails.purchaseplatform.common.adapter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ItemHotSearchBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

public class HotSearchRecyclerAdapter extends BaseRecyclerAdapter<HotSearchBean, ItemHotSearchBinding> {

    private Context mContext;
    private OnClickCallBack callBack;
    private int mType = 0;

    public HotSearchRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_hot_search;
    }

    @Override
    protected void onBindItem(ItemHotSearchBinding binding, HotSearchBean hotSearchBean, int position) {
        binding.setHotSearch(hotSearchBean);
        String text = hotSearchBean.getSearchItem();
        binding.tvSearchItem.setText(text);
        binding.tvSearchItem.setOnClickListener(v -> {

            callBack.onClickCallBack(text);

            Bundle bundle = new Bundle();
            bundle.putInt("search_type", mType);
            bundle.putString("search_key", text);
            ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).with(bundle).navigation();
        });
    }

    public void setSearchType(int type) {
        mType = type;
    }

    public void setListener(OnClickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnClickCallBack {
        void onClickCallBack(String text);
    }

}
