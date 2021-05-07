package com.rails.purchaseplatform.market.adapter.pdetail;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductDetailImgBinding;


/**
 * 求助详情imageAdapter
 * author:wangchao
 */
public class DetailImgAdapter extends BaseRecyclerAdapter<ItemPicture, ItemProductDetailImgBinding> {


    public DetailImgAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_detail_img;
    }

    @Override
    protected void onBindItem(ItemProductDetailImgBinding binding, ItemPicture s, int position) {
        binding.setItem(s);
    }


    public void loadIntoUseFitWidthPlace(final Context context, final String url, final ImageView img, final ImageView place) {

//        Glide.with(context).load(url).placeholder(R.drawable.ic_cart_null).diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        place.setVisibility(View.GONE);
//                        int width = resource.getWidth();
//                        int height = resource.getHeight();
//
//                        //获取imageView的宽
//                        int imageViewWidth = ScreenSizeUtil.getScreenWidth(context) - ScreenSizeUtil.dp2px(context, 20);
//
//                        //计算缩放比例
//                        float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);
//
//                        //计算图片等比例放大后的高
//                        int imageViewHeight = (int) (height * sy);
//                        ViewGroup.LayoutParams params = img.getLayoutParams();
//                        params.height = imageViewHeight;
//                        img.setLayoutParams(params);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                    }
//
//                });

        Glide.with(context).load(url).into(img);
    }

}
