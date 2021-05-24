package com.rails.purchaseplatform.common.utils;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;


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
            imageUrl = "";

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
            imageUrl = "";

        if (!imageUrl.contains("https"))
            imageUrl = "https:" + imageUrl;

        Glide.with(view)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_rect)
                .error(R.drawable.ic_placeholder_rect)
                .into(view);
    }


    /**
     * 绑定地址图片
     *
     * @param view
     * @param imageUrl
     */
    @androidx.databinding.BindingAdapter("imgUrl_s")
    public static void bindImageUrl_s(ImageView view, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl))
            imageUrl = "";

        if (!imageUrl.contains("https"))
            imageUrl = "https:" + imageUrl;

        Glide.with(view)
                .load(imageUrl)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;

                        int screenWidth = ScreenSizeUtil.getScreenWidth(view.getContext());
                        int drawableWidth = bitmapDrawable.getBitmap().getWidth();
                        int drawableHeight = bitmapDrawable.getBitmap().getHeight();

                        float scale = ((float) screenWidth) / ((float) drawableWidth);
                        int displayHeight = (int) (drawableHeight * scale);

                        ViewGroup.LayoutParams params = view.getLayoutParams();
                        params.width = screenWidth;
                        params.height = displayHeight;
                        view.setLayoutParams(params);

                        return false;
                    }
                })
                .placeholder(R.drawable.ic_placeholder_rect)
                .error(R.drawable.ic_placeholder_rect)
                .into(view);
    }
}
