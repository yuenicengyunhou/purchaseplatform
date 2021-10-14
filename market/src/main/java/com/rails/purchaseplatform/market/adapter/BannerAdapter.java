package com.rails.purchaseplatform.market.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author： sk_comic@163.com
 * @date: 2021/9/28
 */
public class BannerAdapter extends com.rails.purchaseplatform.common.widget.banner.adapter.BannerAdapter<String, BannerAdapter.BannerViewHolder> {


    public BannerAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, String imageUrl, int position, int size) {
//        holder.imageView.setImageResource(data.imageRes);
        if (TextUtils.isEmpty(imageUrl))
            imageUrl = "";

        if (!imageUrl.contains("https"))
            imageUrl = "https:" + imageUrl;

        Glide.with(holder.itemView)
                .load(imageUrl)
                .centerInside()
                .error(com.rails.purchaseplatform.common.R.drawable.ic_placeholder)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }

}
