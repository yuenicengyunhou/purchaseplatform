package com.rails.purchaseplatform.common.multiplecolumnslist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rails.purchaseplatform.common.R;

public class MultipleColumnsListActivity extends AppCompatActivity {
    final private String TAG = MultipleColumnsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_columns_list);

    }
}
