package com.rails.purchaseplatform.common.widget.banner.indicator;

import android.view.View;

import com.rails.purchaseplatform.common.widget.banner.config.IndicatorConfig;
import com.rails.purchaseplatform.common.widget.banner.listener.OnPageChangeListener;

import androidx.annotation.NonNull;


public interface Indicator extends OnPageChangeListener {
    @NonNull
    View getIndicatorView();

    IndicatorConfig getIndicatorConfig();

    void onPageChanged(int count, int currentPosition);

}
