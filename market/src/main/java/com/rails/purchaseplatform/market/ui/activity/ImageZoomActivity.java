package com.rails.purchaseplatform.market.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ActivityImageZoomBinding;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;


@Route(path = ConRoute.MARKET.IMAGE_ZOOM)
public class ImageZoomActivity extends BaseErrorActivity<ActivityImageZoomBinding> {
    final private String TAG = ImageZoomActivity.class.getSimpleName();

    final private int START_LOADING = 31;
    final private int FINISH_LOADING = 63;

    private PhotoViewAttacher mAttach;
    private Bitmap mBitmap;
    private String mImageUrl = "";
    private Handler mImageLoadHandler;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mImageUrl = extras.getString("imageUrl");
//        Log.d(TAG, mImageUrl);
    }

    @Override
    protected void initialize(Bundle bundle) {
        mImageLoadHandler = new ImageLoadHandler(this);
        getBitmapFromUrl(mImageUrl);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onClick() {
        binding.ivClose.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 创建新线程把imageURL转成Bitmap
     *
     * @param imgUrl
     */
    public void getBitmapFromUrl(String imgUrl) {
        mAttach = new PhotoViewAttacher(binding.pvZoomImage);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mBitmap = Glide.with(ImageZoomActivity.this)
                            .asBitmap()
                            .load(imgUrl)
                            .submit(960, 960)
                            .get();
                    Message message = new Message();
                    message.what = FINISH_LOADING;
                    mImageLoadHandler.sendMessage(message);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    static class ImageLoadHandler extends Handler {
        private final WeakReference<ImageZoomActivity> mWeakReference;

        public ImageLoadHandler(ImageZoomActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            ImageZoomActivity activity = mWeakReference.get();
            if (msg.what == activity.FINISH_LOADING) {
                if (activity.mBitmap != null)
                    activity.binding.pvZoomImage.setImageBitmap(activity.mBitmap);
                else
                    activity.binding.pvZoomImage.setImageResource(R.drawable.ic_placeholder_rect);
                activity.mAttach.update();
            }
        }
    }

}