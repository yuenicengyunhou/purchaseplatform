package com.rails.purchaseplatform.market.adapter.pdetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.rails.purchaseplatform.market.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ZoomImgVp2Adapter extends RecyclerView.Adapter<ZoomImgVp2Adapter.ZoomImgVp2ViewHolder> {
    final private String TAG = ZoomImgVp2Adapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<Bitmap> mBitmapList = new ArrayList<>();

    public ZoomImgVp2Adapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public ZoomImgVp2ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ZoomImgVp2ViewHolder(LayoutInflater.from(mContext).inflate((R.layout.item_vp2_image_zoom), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ZoomImgVp2ViewHolder holder, int position) {
        if (mBitmapList.get(position) != null) {
            holder.zoomImage.setImage(ImageSource.bitmap(mBitmapList.get(position)));
        } else {
            holder.zoomImage.setImage(ImageSource.resource(R.drawable.ic_placeholder_rect));
        }
        holder.zoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBitmapList.size();
    }

    static class ZoomImgVp2ViewHolder extends RecyclerView.ViewHolder {
        SubsamplingScaleImageView zoomImage;

        ZoomImgVp2ViewHolder(@NonNull View itemView) {
            super(itemView);
            zoomImage = itemView.findViewById(R.id.ssiv_zoom_image);
        }
    }

    // 更新数据
    public void update(ArrayList<Bitmap> data, boolean isClear) {
        if (isClear) {
            mBitmapList.clear();
        }
        mBitmapList.addAll(data);
        notifyDataSetChanged();
    }
}
