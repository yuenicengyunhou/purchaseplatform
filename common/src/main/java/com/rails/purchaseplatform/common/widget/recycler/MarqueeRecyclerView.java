package com.rails.purchaseplatform.common.widget.recycler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * O
 * recyclerView 自己滚动
 *
 * @author： sk_comic@163.com
 * @date: 2021/6/1
 */
public class MarqueeRecyclerView extends BaseRecyclerView {

    Thread thread = null;
    AtomicBoolean shouldContinue = new AtomicBoolean(false);
    Handler mHandler;

    public MarqueeRecyclerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public MarqueeRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {
        //主线程的handler，用于执行Marquee的滚动消息
        int width = (ScreenSizeUtil.getScreenWidth(getContext()) - ScreenSizeUtil.dp2px(getContext(), 56)) >> 2;
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://不论是竖直滚动还是水平滚动，都是偏移5个像素
                        if (canScrollHorizontally(1)) {
                            smoothScrollBy(width, 10);
                        } else
                            smoothScrollToPosition(0);
                        break;
                }
                return false;
            }
        });

        if (thread == null) {
            thread = new Thread() {
                public void run() {
                    while (shouldContinue.get()) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.sendToTarget();
                    }
                    //退出循环时清理handler
                    mHandler = null;
                }
            };
        }
    }


    @Override
    /**
     * 在附到窗口的时候开始滚动
     */
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        shouldContinue.set(true);
        init();
        thread.start();
    }

    @Override
    /**
     * 在脱离窗口时处理相关内容
     */
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopMarquee();
    }

    /**
     * 停止滚动
     */
    public void stopMarquee() {
        shouldContinue.set(false);
        thread = null;
    }
}
