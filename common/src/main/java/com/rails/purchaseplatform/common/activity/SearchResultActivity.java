package com.rails.purchaseplatform.common.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rails.purchaseplatform.common.R;

public class SearchResultActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
    }
}
