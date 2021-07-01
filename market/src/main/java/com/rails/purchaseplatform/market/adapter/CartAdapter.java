package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemMarketCartBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 购物车列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/10
 */
public class CartAdapter extends BaseRecyclerAdapter<CartShopBean, ItemMarketCartBinding> {


    //控制全局
    public static final int CHECK = 0;
    //产品规格弹窗
    public static final int PROPERTY = 1;
    //增加
    public static final int ADD = 2;
    //删除
    public static final int REDUCE = 3;
    //编辑
    public static final int EDIT = 4;
    //商城首页
    public static final int SHOP = 5;
    //删除
    public static final int SUB_DEL = 6;
    //收藏
    public static final int SUB_COLLECT = 7;

    private CartSubAdapter subAdapter;
    private int lastPosition;


    public CartAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_market_cart;
    }

    @Override
    protected void onBindItem(ItemMarketCartBinding binding, CartShopBean cartShopBean, int position) {
        binding.setCart(cartShopBean);


        CartSubAdapter adapter = new CartSubAdapter(mContext);
        binding.recycler.setAdapter(adapter);
        adapter.setMulPositionListener(new MulPositionListener<CartShopProductBean>() {
            @Override
            public void onPosition(CartShopProductBean bean, int len, int... params) {
                int size = adapter.getSize();
                if (params[0] == CartSubAdapter.CHECK) {
                    cartShopBean.isSel.set(isAllChecked(cartShopBean));
                    mulPositionListener.onPosition(bean, len, CHECK);

                } else if (params[0] == CartSubAdapter.PROPERTY) {
                    // TODO: 2021/3/22 产品规格
                    if (mulPositionListener != null) {
                        mulPositionListener.onPosition(bean, len, PROPERTY);
                    }
                } else if (params[0] == CartSubAdapter.ADD) {
                    // TODO: 2021/3/22 数量加减
                    mulPositionListener.onPosition(bean, len, ADD);
                } else if (params[0] == CartSubAdapter.REDUCE) {
                    // TODO: 2021/3/22 数量加减
                    mulPositionListener.onPosition(bean, len, REDUCE);
                } else if (params[0] == CartSubAdapter.EDIT) {
                    // TODO: 2021/3/22 数量加减
                    mulPositionListener.onPosition(bean, len, EDIT);
                } else if (params[0] == CartSubAdapter.SUB_COLLECT) {
                    // TODO: 2021/3/22 收藏
                    subAdapter = adapter;
                    mulPositionListener.onPosition(bean, len, SUB_COLLECT);
                    if (size == 0) {
                        updateRemove(position);
                    }
                } else if (params[0] == CartSubAdapter.SUB_DEL) {
                    // TODO: 2021/3/22 删除
                    subAdapter = adapter;
                    lastPosition = position;
                    mulPositionListener.onPosition(bean, len, SUB_DEL);
                }

            }
        });

        adapter.update((ArrayList) cartShopBean.getSkuList(), true);


        binding.imgLeft.setOnClickListener(v -> {
            checkShopAll(cartShopBean, binding.imgLeft.isChecked());
            if (positionListener != null)
                positionListener.onPosition(cartShopBean, CHECK);
        });

        binding.shopLayout.setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(cartShopBean, SHOP);
        });

        binding.rlPostage.setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(cartShopBean, SHOP);
        });
    }


    /**
     * 商铺全选
     *
     * @param cartShopBean
     * @param isChecked
     */
    private void checkShopAll(CartShopBean cartShopBean, boolean isChecked) {
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
                bean.isSel.set(isChecked);
            }
        } catch (Exception e) {
        }
    }


    /**
     * 购物车是否全选
     *
     * @param isChecked
     */
    public void checkAll(boolean isChecked) {
        for (CartShopBean shopBean : mDataSource) {
            shopBean.isSel.set(isChecked);
            checkShopAll(shopBean, isChecked);
        }
    }


    /**
     * 获取全部
     *
     * @return
     */
    public ArrayList<CartShopBean> getBeans() {
        return mDataSource;
    }


    public boolean isAll() {
        for (CartShopBean bean : mDataSource) {
            boolean isCheck = isAllChecked(bean);
            if (!isCheck)
                return false;
        }
        return true;
    }


    /**
     * 计算总计价格
     *
     * @return
     */
    public double totalPrice() {
        double total = 0;
        for (CartShopBean bean : mDataSource) {
            total += shopPrice(bean);
        }
        return total;
    }


    /**
     * 计算一个店的价格
     *
     * @return
     */
    private double shopPrice(CartShopBean cartShopBean) {
        double total = 0;
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
//                if (bean.canSel.get() == null)
//                    continue;
                if (bean.isSel.get() == null)
                    continue;
//                if (bean.isSel.get() && bean.canSel.get()) {
                if (bean.isSel.get()) {
                    total += bean.num.get() * bean.getSellPrice();
                }
            }
        } catch (Exception e) {
            return total;
        }
        return total;
    }


    /**
     * 商店商品是否全部被选中
     */
    private boolean isAllChecked(CartShopBean cartShopBean) {
        try {
            ArrayList<CartShopProductBean> beans = (ArrayList<CartShopProductBean>) cartShopBean.getSkuList();
            for (CartShopProductBean bean : beans) {
                if (bean.isSel == null)
                    return false;
                if (!bean.isSel.get()) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    protected void onBindView(ItemMarketCartBinding binding) {
        super.onBindView(binding);
        binding.recycler.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        binding.recycler.addItemDecoration(new SpaceDecoration(mContext, 1, R.color.line_gray));
    }


    /**
     * 更新子dapter列表
     *
     * @param position
     */
    public void updateSubAdater(int position) {
        if (subAdapter != null) {
            subAdapter.updateRemove(position);
            if (subAdapter.getItemCount() == 0) {
                updateRemove(lastPosition);
            }
        }
    }


    /**
     * 更新邮费差额
     */
    public void updateShopPrice() {
        for (CartShopBean shopBean : mDataSource) {
            double total = shopPrice(shopBean);
            String freightS = shopBean.getFreightPrice();
            double freight = 0D;
            try {
                if (TextUtils.isEmpty(freightS)) {
                    freight = 0;
                } else
                    freight = Double.parseDouble(freightS);
            } catch (Exception e) {

            }
            if (total >= freight) {
                shopBean.dprice.set(String.valueOf(0));
                shopBean.isshow.set(false);
            } else {
                shopBean.dprice.set(DecimalUtil.formatDouble(freight - total));
                shopBean.isshow.set(true);
            }
        }
    }


    /**
     * 是否可以下一步
     *
     * @return
     */
    public boolean isNext() {
        if (mDataSource.isEmpty())
            return false;
        for (CartShopBean shopBean : mDataSource) {
            for (CartShopProductBean productBean : shopBean.getSkuList()) {
                if (productBean.isSel.get())
                    if (shopBean.isshow.get())
                        return false;
            }
        }
        return true;
    }

}
