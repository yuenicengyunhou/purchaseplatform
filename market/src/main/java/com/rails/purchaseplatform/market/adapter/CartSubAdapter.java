package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketCartSubCopyBinding;

import java.text.MessageFormat;

/**
 * author： sk_comic@163.com
 * date: 2021/3/11
 */
public class CartSubAdapter extends BaseRecyclerAdapter<CartShopProductBean, ItemMarketCartSubCopyBinding> {

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
        return R.layout.item_market_cart_sub_copy;
    }

    @Override
    protected void onBindItem(ItemMarketCartSubCopyBinding binding, CartShopProductBean productBean, int position) {
        binding.setProduct(productBean);

        String property = productBean.getAttributesName();
        if (TextUtils.isEmpty(property)) {
            binding.tvProperty.setVisibility(View.INVISIBLE);
        } else {
            binding.tvProperty.setVisibility(View.VISIBLE);
        }

        String price = DecimalUtil.formatDouble(productBean.getSellPrice());
        String unit = productBean.getUnitName();
        int size;
        int fontSize;
//        if (SystemUtil.isPad(mContext)) {
//            fontSize = ScreenSizeUtil.sp2px(8, mContext);
//        } else {
//            fontSize = 10;
//        }
//        binding.tvPriceBig.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
//        binding.tvPriceSmall.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        if (SystemUtil.isPad(mContext)) {
           binding.tvPriceBig.setTextSize(20);
           binding.tvPriceSmall.setTextSize(14);
        }
        String bigPrice = price.substring(0, price.indexOf("."));
        String smallPrice = price.substring(price.indexOf("."));

        binding.tvPriceBig.setText(MessageFormat.format("¥ {0}", bigPrice));
        String finalSmallPrice = smallPrice + (TextUtils.isEmpty(unit) ? "" : " /" + unit);
        binding.tvPriceSmall.setText(finalSmallPrice);

        if (productBean.canSel.get()) {
            binding.tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_black));
            binding.tvPriceBig.setTextColor(mContext.getResources().getColor(R.color.font_red));
//            binding.tvPriceBig.setTextSize(16);
//            TextPaint tp = binding.tvPriceBig.getPaint();
//            tp.setFakeBoldText(true);
//            binding.tvPriceSmall.setVisibility(View.VISIBLE);
        } else {
            binding.tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_gray_invalid));
            binding.tvPriceBig.setTextColor(mContext.getResources().getColor(R.color.font_black));
//            binding.tvPriceBig.setText("商品已失效");
//            binding.tvPriceBig.setTextSize(14);
//            TextPaint tp = binding.tvPriceBig.getPaint();
//            tp.setFakeBoldText(false);
//            binding.tvPriceSmall.setVisibility(View.GONE);
        }
        binding.imgLeft.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                boolean isChecked = binding.imgLeft.isChecked();
                productBean.isSel.set(isChecked);
                mulPositionListener.onPosition(productBean, position, CHECK);
            }

        });


        binding.tvProperty.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(productBean, position, PROPERTY);
            }
        });


        binding.tvAdd.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(productBean, position, ADD);
            }
        });


        binding.tvReduce.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(productBean, position, REDUCE);
            }
        });

        binding.etNum.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(productBean, position, EDIT);
            }
        });

        binding.btnCollect.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(productBean, position, SUB_COLLECT);
            }

        });

        binding.btnDel.setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(productBean, position, SUB_DEL);
            }

        });


        binding.getRoot().setOnClickListener(v -> {
            if (TextUtils.isEmpty(productBean.getItemId())) {
                ToastUtil.showCenter(mContext, "商品不存在或已下架");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("itemId", productBean.getItemId());
            ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
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

}
