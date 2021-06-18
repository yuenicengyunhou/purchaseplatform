package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
import com.rails.purchaseplatform.common.widget.tags.OnTagClickListener;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductPropertyBinding;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 产品规格adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class PropertyAdapter extends BaseRecyclerAdapter<SpecificationPopBean, ItemProductPropertyBinding> {
    private final String TAG = PropertyAdapter.class.getSimpleName();

    private Context mContext;

    private ArrayList<ItemSkuInfo> mItemSkuInfoList;
    private OnItemClicked onItemClicked;

    public PropertyAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, SpecificationPopBean specificationPopBean, int p) {

        binding.cbExpand.setVisibility(View.GONE);
        binding.tvName.setText(specificationPopBean.getAttrName());
        binding.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        binding.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        PropertySubAdapter adapter = new PropertySubAdapter(mContext);
        binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        binding.flow.setAdapter(adapter);
        ArrayList<SpecificationValue> tags = new ArrayList<>(specificationPopBean.getSpecificationValue());
        adapter.update(tags);

        for (SpecificationValue bean : tags) {
            if (bean.isSelect()) {
                binding.flow.selectSingleCheck(tags.indexOf(bean));
                break;
            }
        }

        binding.flow.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                boolean select = mDataSource.get(p).getSpecificationValue().get(position).isSelect();
                if (!select) {
                    for (int j = 0; j < mDataSource.get(p).getSpecificationValue().size(); j++) {
                        if (mDataSource.get(p).getSpecificationValue().get(j).isSelect()) {
                            mDataSource.get(p).getSpecificationValue().get(j).setSelect(false);
                        }
                    }
                }
                mDataSource.get(p).getSpecificationValue().get(position).setSelect(!select);
                notifyDataSetChanged();
                ItemSkuInfo itemSkuInfo = checkSkuInfo(mDataSource, mItemSkuInfoList);
                onItemClicked.onItemClicked(itemSkuInfo);
            }
        });
    }

    /**
     * 返回列表数据
     */
    public ArrayList<SpecificationPopBean> getListData() {
        return mDataSource;
    }

    public void setItemSkuInfoList(ArrayList<ItemSkuInfo> itemSkuInfos) {
        mItemSkuInfoList = itemSkuInfos;
    }


    /**
     * 获取SkuId
     *
     * @param beans
     */
    private ItemSkuInfo checkSkuInfo(ArrayList<SpecificationPopBean> beans, ArrayList<ItemSkuInfo> itemSkuInfos) {
        if (itemSkuInfos == null || itemSkuInfos.size() == 0) {
            return null;
        }

        ItemSkuInfo itemSkuInfo = null;

        //选中属性map
        HashMap<String, String> hashMapSelect = new HashMap<>();
        for (SpecificationPopBean popBean : beans) {
            String id = popBean.getAttrId();
            for (SpecificationValue value : popBean.getSpecificationValue()) {
                if (value.isSelect()) {
                    hashMapSelect.put(popBean.getAttrId(), value.getAttrValueId());
                }
            }
        }

        ArrayList<HashMap<String, String>> allHash = new ArrayList<>();
        for (ItemSkuInfo skuInfo : itemSkuInfos) {
            HashMap<String, String> hashMap = new HashMap<>();
            String stringName = skuInfo.getAttributes();
            String[] strings = stringName.split(";");
            for (String s : strings) {
                String[] subs = s.split(":");
                hashMap.put(subs[0], subs[1]);
            }
            allHash.add(hashMap);
        }

        boolean isMatch = false;
        int lastPosition = 0;
        for (HashMap<String, String> map : allHash) { // 外
            for (String key : hashMapSelect.keySet()) { // 内
                String value = map.get(key);
                if (TextUtils.isEmpty(value)) {
                    isMatch = false;
                    lastPosition++;
                    break;
                }
                if (value.equals(hashMapSelect.get(key))) {
                    isMatch = true;
                    continue;
                } else {
                    isMatch = false;
                    lastPosition++;
                    break;
                }
            }

            if (isMatch) {
                itemSkuInfo = itemSkuInfos.get(lastPosition);
            }
        }

        return itemSkuInfo;
    }


    /**
     * 设置监听接口
     *
     * @param onItemClicked
     */
    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    public interface OnItemClicked {
        void onItemClicked(ItemSkuInfo itemSkuInfo);
    }

}
