package com.rails.purchaseplatform.market.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rails.purchaseplatform.common.widget.RatioImage;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader4ProductDetails extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load((String) path).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        RatioImage image = new RatioImage(context);
        image.setW(24);
        image.setH(25);
        return image;
    }
}
