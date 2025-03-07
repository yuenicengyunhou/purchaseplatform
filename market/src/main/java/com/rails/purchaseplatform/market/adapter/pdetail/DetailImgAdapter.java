package com.rails.purchaseplatform.market.adapter.pdetail;

import android.content.Context;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductDetailImgBinding;


/**
 * 求助详情imageAdapter
 * author:wangchao
 */
public class DetailImgAdapter extends BaseRecyclerAdapter<ItemPicture, ItemProductDetailImgBinding> {

    private ItemClickListener mItemClickListener;


    public DetailImgAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_detail_img;
    }

    @Override
    protected void onBindItem(ItemProductDetailImgBinding binding, ItemPicture s, int position) {
        if (s.getBitmap() == null) {
            binding.ssivDetails.setImage(ImageSource.resource(R.drawable.ic_placeholder_rect));
            return;
        }
        binding.ssivDetails.setImage(ImageSource.bitmap(s.getBitmap()));
        binding.ssivDetails.setZoomEnabled(false);
        binding.ssivDetails.setOnClickListener(v -> {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(position);
//            Bundle bundle = new Bundle();
//            bundle.putString("imageUrl", s.getPictureUrl());
//            ARouter.getInstance().build(ConRoute.MARKET.IMAGE_ZOOM).with(bundle).navigation();
        });
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
