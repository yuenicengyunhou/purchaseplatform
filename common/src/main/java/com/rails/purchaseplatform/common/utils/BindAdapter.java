package com.rails.purchaseplatform.common.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;

/**
 * 绑定
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/18
 */
public class BindAdapter {

    /**
     * 绑定资源图片
     *
     * @param view
     * @param imgRes
     */
    @androidx.databinding.BindingAdapter("imgRes")
    public static void bindImageUrl(ImageView view, int imgRes) {
        RequestOptions options =
                new RequestOptions()
                        .centerCrop()
                        .dontAnimate();

        Glide.with(view)
                .load(imgRes)
                .apply(options)
                .into(view);
    }


    /**
     * 绑定地址图片
     *
     * @param view
     * @param imageUrl
     */
    @androidx.databinding.BindingAdapter("imgUrl")
    public static void bindImageUrl(ImageView view, String imageUrl) {
        if (!imageUrl.contains("https:"))
            imageUrl = "https:" + imageUrl;
        RequestOptions options =
                new RequestOptions()
                        .centerCrop()
                        .dontAnimate();

        Glide.with(view)
                .load(imageUrl)
                .apply(options)
                .into(view);
    }

}
