package com.rails.purchaseplatform.market.ui.activity;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.BASE64;
import com.rails.purchaseplatform.market.databinding.ActivityMarketRankBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 商品排行榜列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/6/7
 */
public class RankActivity extends BaseErrorActivity<ActivityMarketRankBinding> {
    @Override
    protected void initialize(Bundle bundle) {

        String bytes =BASE64.encode( readFile("a.png"));
        binding.encode.setText(bytes);
    }

    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    public byte[] readFile(String filename) {
        byte[] buffer = null;
        AssetManager am = getAssets();
        try {
            InputStream inputStream = am.open(filename);
            int length = inputStream.available();
            buffer = new byte[length];
            inputStream.read(buffer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return buffer;
    }


}
