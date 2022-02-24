package com.rails.purchaseplatform.market.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.utils.StatusBarCompat;
import com.rails.purchaseplatform.framwork.base.XiaoMiStatusBar;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.pdetail.ZoomImgVp2Adapter;
import com.rails.purchaseplatform.market.databinding.ActivityImageZoomBinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


@Route(path = ConRoute.MARKET.IMAGE_ZOOM)
public class ImageZoomActivity extends BaseErrorActivity<ActivityImageZoomBinding> {
    final private String TAG = ImageZoomActivity.class.getSimpleName();

    final private int START_LOADING = 31;
    final private int FINISH_LOADING = 63;
    final private int FINISH_LOADING_2 = 65;

    private String mImageUrl = "";
    private Bitmap mBitmap;

    private ArrayList<String> mImageUrlList = new ArrayList<>();
    private ArrayList<Bitmap> mBitmapList = new ArrayList<>();
    private int mPosition = 0;

    private ZoomImgVp2Adapter zoomImgVp2Adapter;

    private Thread mConvertToBitmap;

    private final Handler mImageLoadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == FINISH_LOADING) {
                if (mBitmap != null) {
                    binding.ssivZoomImage.setImage(ImageSource.bitmap(mBitmap));
                } else {
                    binding.ssivZoomImage.setImage(ImageSource.resource(R.drawable.ic_placeholder_rect));
                }
                return true;
            } else if (msg.what == FINISH_LOADING_2) {
                zoomImgVp2Adapter.update(mBitmapList, true);
                binding.viewPager.setCurrentItem(mPosition);
                binding.tvCurrentCount.setText(MessageFormat.format("{0}/{1}", mPosition + 1, mBitmapList.size()));
                return true;
            }
            return false;
        }
    });

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mImageUrl = extras.getString("imageUrl");
        ArrayList<String> list = extras.getStringArrayList("imageUrlList");
        if (list != null) mImageUrlList.addAll(list);
        mPosition = extras.getInt("position");
    }

    @Override
    protected void initialize(Bundle bundle) {
//        if (XiaoMiStatusBar.isXiaomi())
//            MIUISetStatusBarLightMode(getWindow(), true);
//        else
//            setDarkStatusBar(android.R.color.white);

        if (!TextUtils.isEmpty(mImageUrl)) {
            binding.ssivZoomImage.setVisibility(View.VISIBLE);
        } else {
            int size = mImageUrlList.size();
            binding.viewPager.setVisibility(View.VISIBLE);
            zoomImgVp2Adapter = new ZoomImgVp2Adapter(ImageZoomActivity.this);
            binding.viewPager.setAdapter(zoomImgVp2Adapter);
            binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    binding.tvCurrentCount.setText(MessageFormat.format("{0}/{1}", position + 1, size));
                }
            });
        }
        getBitmapFromUrl();
    }

    @Override
    protected int getColor() {
        return XiaoMiStatusBar.isXiaomi()
                ? android.R.color.transparent
                : android.R.color.white;
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
        binding.ssivZoomImage.setOnClickListener(v -> finish());
        binding.ivClose.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        if (mConvertToBitmap != null) {
            mConvertToBitmap.interrupt();
            mConvertToBitmap = null;
        }
        super.onDestroy();
    }

    /**
     * 创建新线程把imageURL转成Bitmap
     */
    public void getBitmapFromUrl() {
        mConvertToBitmap = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!TextUtils.isEmpty(mImageUrl)) {
                        mBitmap = Glide.with(ImageZoomActivity.this)
                                .asBitmap()
                                .load(mImageUrl)
                                .submit(960, 960)
                                .get();
                    } else {
                        for (String url : mImageUrlList) {
                            if (url != null && !url.startsWith("https:"))
                                url = "https:" + url;
                            Bitmap bitmap = Glide.with(ImageZoomActivity.this)
                                    .asBitmap()
                                    .load(url)
                                    .submit(960, 960)
                                    .get();
                            mBitmapList.add(bitmap);
                        }
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    if (!TextUtils.isEmpty(mImageUrl)) {
                        message.what = FINISH_LOADING;
                    } else {
                        message.what = FINISH_LOADING_2;
                    }
                    mImageLoadHandler.sendMessage(message);
                }
            }
        });
        mConvertToBitmap.setDaemon(false);
        mConvertToBitmap.start();
    }

    private void setDarkStatusBar(int color) {
        int statusBarColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarColor = ContextCompat.getColor(this, color);
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            statusBarColor = ContextCompat.getColor(this, color);
        }
        StatusBarCompat.compat(this, statusBarColor);
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

}