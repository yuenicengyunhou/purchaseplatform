package com.rails.purchaseplatform.common.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.rails.purchaseplatform.common.R;


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
        Glide.with(view)
                .load(imgRes)
                .centerInside()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
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
        if (TextUtils.isEmpty(imageUrl))
            imageUrl ="";

        if (!imageUrl.contains("https"))
            imageUrl = "https:" + imageUrl;

        Glide.with(view)
                .load(imageUrl)
                .centerInside()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(view);
    }


    /**
     * 绑定地址图片
     *
     * @param view
     * @param imageUrl
     */
    @androidx.databinding.BindingAdapter("imgUrl_r")
    public static void bindImageUrl_r(ImageView view, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl))
            imageUrl ="";

        if (!imageUrl.contains("https"))
            imageUrl = "https:" + imageUrl;

        Glide.with(view)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_rect)
                .error(R.drawable.ic_placeholder_rect)
                .into(view);
    }

}
