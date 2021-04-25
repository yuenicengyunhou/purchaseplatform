package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketCartSubBinding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/11
 */
public class CartSubAdapter extends BaseRecyclerAdapter<CartShopProductBean, ItemMarketCartSubBinding> {

    //选中
    public static final int CHECK = 0;
    //产品规格弹窗
    public static final int PROPERTY = 1;
    //增加
    public static final int ADD = 2;
    //删除
    public static final int REDUCE = 3;
    //编辑
    public static final int EDIT = 4;
    //删除
    public static final int SUB_DEL = 6;
    //收藏
    public static final int SUB_COLLECT = 7;


    public CartSubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_cart_sub;
    }

    @Override
    protected void onBindItem(ItemMarketCartSubBinding binding, CartShopProductBean productBean, int position) {
        binding.setProduct(productBean);

        String property = productBean.getAttributesName();
        if (TextUtils.isEmpty(property)){
            binding.tvProperty.setVisibility(View.GONE);
        }else{
            binding.tvProperty.setVisibility(View.VISIBLE );
        }

        String price = DecimalUtil.formatDouble(productBean.getSellPrice());
        binding.tvPrice.setText(DecimalUtil.formatStrSize("¥ ", price, "", 18));

        binding.imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    boolean isChecked = binding.imgLeft.isChecked();
                    productBean.isSel.set(isChecked);
                    mulPositionListener.onPosition(productBean, position, CHECK);
                }

            }
        });


        binding.tvProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    mulPositionListener.onPosition(productBean, position, PROPERTY);
                }
            }
        });


        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    mulPositionListener.onPosition(productBean, position, ADD);
                }
            }
        });


        binding.tvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    mulPositionListener.onPosition(productBean, position, REDUCE);
                }
            }
        });

        binding.etNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    mulPositionListener.onPosition(productBean, position, EDIT);
                }
            }
        });

        binding.btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    mulPositionListener.onPosition(productBean, position, SUB_COLLECT);
                }

            }
        });

        binding.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mulPositionListener != null) {
                    mulPositionListener.onPosition(productBean, position, SUB_DEL);
                }

            }
        });

    }

    /**
     * 获取对象
     *
     * @param position
     * @return
     */
    public CartShopProductBean getBean(int position) {
        if (mDataSource.size() > position)
            return mDataSource.get(position);
        return null;
    }


    public int getSize() {
        return mDataSource.size();
    }


//    public void canSwipe(boolean isSwipe) {
//        this.isSwipe = isSwipe;
//        notifyDataSetChanged();
//    }
}
