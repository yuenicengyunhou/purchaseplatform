package com.rails.purchaseplatform.market.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rails.purchaseplatform.common.widget.RatioImage;
import com.youth.banner.loader.ImageLoader;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/16
 */
public class GlideImageLoader extends ImageLoader {

    private int mWidth;
    private int mHeight;

    private GlideImageLoader() {
    }

    private static class GlideImageLoaderHolder {
        private static final GlideImageLoader INSTANCE = new GlideImageLoader();
    }

    public static GlideImageLoader getInstance() {
        return GlideImageLoaderHolder.INSTANCE;
    }

    public GlideImageLoader setWidthHeight(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = (String) path;
        if (TextUtils.isEmpty(url))
            url = "";

        if (!url.contains("https"))
            url = "https:" + url;
        Glide.with(context).load(url).into(imageView);
    }


    @Override
    public ImageView createImageView(Context context) {
        RatioImage image = new RatioImage(context);
        image.setW(mWidth);
        image.setH(mHeight);
        return image;
    }
}
